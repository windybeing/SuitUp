using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Forms;
using MaterialDesignColors.WpfExample.Domain;
using Emgu.CV;
using Emgu.CV.Structure;
using Chpoi.SuitUp.Source;
using Chpoi.SuitUp.Entity;
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.Util;

namespace chpoi.suitup.ui
{
    /// <summary>
    /// Interaction logic for BuyerWindow.xaml
    /// </summary>
    public partial class BuyerWindow : Window
    {
        public BuyerWindow()
        {
            InitializeComponent();
            try
            {
                //从内存中初始化信息
                this.OldClientName.Text = SourceManager.client.clientname;
                if (SourceManager.client.age == 0)
                {
                    this.OldAge.Text = "";
                }
                else
                {
                    this.OldAge.Text = SourceManager.client.age.ToString();
                }
                this.OldAddress.Text = SourceManager.client.address;
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试");
                LoginWindow lW = new LoginWindow();
                lW.Show();
                this.Close();
                return;
            }
        }

        private void BuyerInformationButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {

                BuyerWindow bW = new BuyerWindow();
                bW.Show();
                this.Close();
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试");
                LoginWindow lW = new LoginWindow();
                lW.Show();
                this.Close();
                return;
            }
        }
        private void BuyerOrderButtonClick(object sender, RoutedEventArgs e)
        {
            UserOrderInterface uOI = new UserOrderInterface();
            uOI.Show();
            this.Close();
        }
        private void ShoppingCartButtonClick(object sender, RoutedEventArgs e)
        {
            ShoppingcartInterface sI = new ShoppingcartInterface();
            sI.Show();
            this.Close();
        }
        private void Window_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            this.DragMove();
        }
        private void CloseButtonClick(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void MinimizeButtonClick(object sender, RoutedEventArgs e)
        {
            this.WindowState = WindowState.Minimized;
        }

        private void MainPageButtonClick(object sender, RoutedEventArgs e)
        {
            GoodsWindow bW = new GoodsWindow();
            bW.Show();
            this.Close();
        }
        private void ModifyButtonClick(object sender, RoutedEventArgs e)
        {
            //点击修改按钮，转换为可编辑文本

            InputAge.Text = OldAge.Text;
            InputAddress.Text = OldAddress.Text;
            InputClientName.Text = OldClientName.Text;

            ClientNameSP.Visibility = Visibility.Collapsed;
            AgeSP.Visibility = Visibility.Collapsed;
            AddressSP.Visibility = Visibility.Collapsed;
            ModifyClientNameSP.Visibility = Visibility.Visible;
            ModifyAgeSP.Visibility = Visibility.Visible;
            ModifyAddressSP.Visibility = Visibility.Visible;
            ModifyPasswordSP.Visibility = Visibility.Visible;
            //切换按钮
            ModifyButton.Visibility = Visibility.Collapsed;
            SaveChangesButton.Visibility = Visibility.Visible;
            CancelchangesButton.Visibility = Visibility.Visible;
        }
        private void SaveChangesButtonClick(object sender, RoutedEventArgs e)
        {
            //保存修改后的信息
            try
            {
                string NewClientName = InputClientName.Text;
                int NewAge;
                if (NewClientName == "")
                {
                    System.Windows.MessageBox.Show("姓名不能为空");
                    return;
                }
                //todo email
                if (InputAddress.Text == "")
                {
                    System.Windows.MessageBox.Show("地址不能为空");
                    return;
                }

                if (InputAge.Text == "")
                {
                    System.Windows.MessageBox.Show("年龄不能为空");
                    return;
                }

                if (InputPassword.Password == "")
                {
                    System.Windows.MessageBox.Show("密码不能为空");
                    return;
                }

                if (InputAge.Text == "")
                {
                    NewAge = SourceManager.client.age;
                }
                else
                {
                    try
                    {
                        int.Parse(InputAge.Text);
                    }
                    catch
                    {
                        System.Windows.MessageBox.Show("年龄不合法！");
                        return;
                    }
                    NewAge = int.Parse(InputAge.Text);
                }
                string NewAddress = InputAddress.Text;
                string NewPassword = InputPassword.Password;

                //保存信息到服务器
                ClientService cS = ServiceFactory.GetClientService();
                string retMessage = cS.ModifyInfor(NewClientName, NewAge, NewAddress, NewPassword);
                if (!cS.IsSuccess(retMessage))
                {
                    if (SourceManager.ErrorMessage[0] == 'E')
                    {
                        System.Windows.MessageBox.Show("系统错误，请稍后再试。");
                        LoginWindow lW = new LoginWindow();
                        lW.Show();
                        this.Close();
                        return;
                    }
                    System.Windows.MessageBox.Show(SourceManager.ErrorMessage);
                    return;
                }

                SourceManager.client.password = NewPassword;
                SourceManager.client.clientname = NewClientName;
                SourceManager.client.age = NewAge;
                SourceManager.client.address = NewAddress;

                //显示修改后信息
                this.OldClientName.Text = SourceManager.client.clientname;
                //this.OldPassword.Text = SourceManager.client.password;
                if (SourceManager.client.age == 0)
                {
                    this.OldAge.Text = "";
                }
                else
                {
                    this.OldAge.Text = SourceManager.client.age.ToString();
                }
                this.OldAddress.Text = SourceManager.client.address;

                //切换到不可编辑界面
                ClientNameSP.Visibility = Visibility.Visible;
                AgeSP.Visibility = Visibility.Visible;
                AddressSP.Visibility = Visibility.Visible;

                ModifyClientNameSP.Visibility = Visibility.Collapsed;
                ModifyAgeSP.Visibility = Visibility.Collapsed;
                ModifyAddressSP.Visibility = Visibility.Collapsed;
                ModifyPasswordSP.Visibility = Visibility.Collapsed;


                SaveChangesButton.Visibility = Visibility.Collapsed;
                CancelchangesButton.Visibility = Visibility.Collapsed;
                ModifyButton.Visibility = Visibility.Visible;
            }
            catch
            {
                System.Windows.MessageBox.Show("修改买家信息错误！");
            }
        }
        private void CancelChangesButtonClick(object sender, RoutedEventArgs e)
        {
            //切换到不可编辑界面
            try
            {
                ClientNameSP.Visibility = Visibility.Visible;
                AgeSP.Visibility = Visibility.Visible;
                AddressSP.Visibility = Visibility.Visible;

                ModifyClientNameSP.Visibility = Visibility.Collapsed;
                ModifyAgeSP.Visibility = Visibility.Collapsed;
                ModifyAddressSP.Visibility = Visibility.Collapsed;
                ModifyPasswordSP.Visibility = Visibility.Collapsed;


                SaveChangesButton.Visibility = Visibility.Collapsed;
                CancelchangesButton.Visibility = Visibility.Collapsed;
                ModifyButton.Visibility = Visibility.Visible;
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试。");
                LoginWindow lW = new LoginWindow();
                lW.Show();
                this.Close();
                return;
            }
        }
        private void PersonalInforButtonClick(object sender, RoutedEventArgs e)
        {

        }
        private void BodyParametersClick(object sender, RoutedEventArgs e)
        {
            BuyerInterfaceBodyParameters bIBP = new BuyerInterfaceBodyParameters();
            bIBP.Show(); 
            this.Close();
        }
        private void UploadAvatarButtonClick(object sender, RoutedEventArgs e)
        {
            try{
            //上传头像
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "All Image Files|*.bmp;*.ico;*.gif;*.jpeg;*.jpg;*.png;*.tif;*.tiff|" +
"Windows Bitmap(*.bmp)|*.bmp|" +
"Windows Icon(*.ico)|*.ico|" +
"Graphics Interchange Format (*.gif)|(*.gif)|" +
"JPEG File Interchange Format (*.jpg)|*.jpg;*.jpeg|" +
"Portable Network Graphics (*.png)|*.png|" +
"Tag Image File Format (*.tif)|*.tif;*.tiff";
            openFileDialog.Title = "选择图片";
            openFileDialog.FilterIndex = 1;
            openFileDialog.RestoreDirectory = true;
            DialogResult result = openFileDialog.ShowDialog();
            if (result == System.Windows.Forms.DialogResult.Cancel)
            {
                return;
            }
            string fileName = openFileDialog.FileName;
                AvatarImage.Source = new BitmapImage(new Uri(fileName, UriKind.RelativeOrAbsolute));
            }
            catch
            {
                System.Windows.MessageBox.Show("Upload Failed!");
            }
        }
        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            //注销
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }
    }
}
