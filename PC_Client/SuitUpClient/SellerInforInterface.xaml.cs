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
    /// Interaction logic for SellerInforWindow.xaml
    /// </summary>
    public partial class SellerInforWindow : Window
    {
        public SellerInforWindow()
        {
            InitializeComponent();
            try
            {
                //从内存中初始化信息
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
            }
            catch
            {
                MessageBox.Show("初始化买家信息错误！");
            }
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

        private void ModifyButtonClick(object sender, RoutedEventArgs e)
        {
            //点击修改按钮，转换为可编辑文本
            ClientNameSP.Visibility = Visibility.Collapsed;
            AgeSP.Visibility = Visibility.Collapsed;
            AddressSP.Visibility = Visibility.Collapsed;
            PasswordSP.Visibility = Visibility.Collapsed;

            ModifyClientNameSP.Visibility = Visibility.Visible;
            ModifyAgeSP.Visibility = Visibility.Visible;
            ModifyAddressSP.Visibility = Visibility.Visible;
            ModifyPasswordSP.Visibility = Visibility.Visible;

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
                        MessageBox.Show("年龄不合法！");
                        return;
                    }
                    NewAge = int.Parse(InputAge.Text);
                }
                string NewAddress = InputAddress.Text;
                string NewPassword = InputPassword.Password;

                //保存信息到服务器
                ClientService cS = ServiceFactory.GetClientService();
                cS.ModifyInfor(NewClientName, NewAge, NewAddress, NewPassword);
                Client c = new Client(NewPassword, NewClientName, NewAge, NewAddress);
                SourceManager.client = c;

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
                PasswordSP.Visibility = Visibility.Visible;

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
                MessageBox.Show("修改买家信息错误！");
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
                PasswordSP.Visibility = Visibility.Visible;

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
                MessageBox.Show("系统错误，请稍后再试。");
            }
        }
        private void PersonalInforButtonClick(object sender, RoutedEventArgs e)
        {

        }
        private void UploadAvatarButtonClick(object sender, RoutedEventArgs e)
        {
            //上传头像
            try
            {
                string sidePhotoPath = InputAvatarTextBox.Text;
                Image<Bgr, byte> srcImage = new Image<Bgr, byte>(sidePhotoPath);
                AvatarImage.Source =  new BitmapImage(new Uri(sidePhotoPath, UriKind.RelativeOrAbsolute));
                MessageBox.Show("Upload Compelete!");
            }
            catch
            {
                MessageBox.Show("Upload Failed!");
            }
            //to do with server
        }
        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            //注销
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }
        private void OrderManageButtonClick(object sender, RoutedEventArgs e)
        {

        }
        private void GoodsManageButtonClick(object sender, RoutedEventArgs e)
        {
        }
        private void SellerInforButtonClick(object sender, RoutedEventArgs e)
        {
        }
    }
}
