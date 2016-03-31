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
using System.IO;

namespace chpoi.suitup.ui
{
    /// <summary>
    /// Interaction logic for SellerModifyGoodsInterface.xaml
    /// </summary>
    public partial class SellerModifyGoodsInterface : Window
    {
        public SellerModifyGoodsInterface()
        {
            InitializeComponent();
            try
            {
                //从内存中初始化信息
                BitmapImage myimg = ByteArrayToBitmapImage(SourceManager.SellerCurSuit.photo);
                AvatarImage.Source = myimg;
                OldSuitName.Text = SourceManager.SellerCurSuit.suitname;
                OldPrice.Text = SourceManager.SellerCurSuit.price.ToString();
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试！");
            }
        }
        private void OrderManageButtonClick(object sender, RoutedEventArgs e)
        {
            SellerOrderWindow sOW = new SellerOrderWindow();
            sOW.Show();
            this.Close();
        }
        private void GoodsManageButtonClick(object sender, RoutedEventArgs e)
        {
            SellerGoodsWindow sGW = new SellerGoodsWindow();
            sGW.Show();
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

        private void ModifyButtonClick(object sender, RoutedEventArgs e)
        {
            //点击修改按钮，转换为可编辑文本
            try
            {
                InputSuitName.Text = OldSuitName.Text;
                InputPrice.Text = OldPrice.Text;
                SuitNameSP.Visibility = Visibility.Collapsed;
                PriceSP.Visibility = Visibility.Collapsed;

                ModifySuitNameSP.Visibility = Visibility.Visible;
                ModifyPriceSP.Visibility = Visibility.Visible;

                ModifyButton.Visibility = Visibility.Collapsed;
                SaveChangesButton.Visibility = Visibility.Visible;
                CancelchangesButton.Visibility = Visibility.Visible;
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
        private void SaveChangesButtonClick(object sender, RoutedEventArgs e)
        {
            //保存修改后的信息
            try
            {
                string NewSuitName = InputSuitName.Text;
                double NewPrice;
                if (InputPrice.Text == "")
                {
                    NewPrice = SourceManager.client.age;
                }
                else
                {
                    try
                    {
                        double.Parse(InputPrice.Text);
                    }
                    catch
                    {
                        System.Windows.MessageBox.Show("价格不合法！");
                        return;
                    }
                    NewPrice = double.Parse(InputPrice.Text);
                }

                //保存信息到服务器
                GoodsService gS = ServiceFactory.GetGoodsService();


                string retMessage = gS.ModifyInfor(NewSuitName, NewPrice, SourceManager.SellerCurSuit._id);
                if (!gS.IsSuccess(retMessage))
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
                SourceManager.SellerSuits = new List<Suit>();
                SourceManager.sellerSuitEnd = false;
                SourceManager.sellerSuitPageMax =-1;
                SourceManager.sellerLastPageCount = 0;
                System.Windows.MessageBox.Show("修改商品成功。");
                //todo 

                //显示修改后信息
                this.OldSuitName.Text = NewSuitName;
                //this.OldPassword.Text = SourceManager.client.password;

                OldPrice.Text = NewPrice.ToString(); ;

                //切换到不可编辑界面
                SuitNameSP.Visibility = Visibility.Visible;
                PriceSP.Visibility = Visibility.Visible;


                ModifySuitNameSP.Visibility = Visibility.Collapsed;
                ModifyPriceSP.Visibility = Visibility.Collapsed;

                SaveChangesButton.Visibility = Visibility.Collapsed;
                CancelchangesButton.Visibility = Visibility.Collapsed;
                ModifyButton.Visibility = Visibility.Visible;
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试");
            }
        }
        private void CancelChangesButtonClick(object sender, RoutedEventArgs e)
        {
            //切换到不可编辑界面
            try
            {
                SuitNameSP.Visibility = Visibility.Visible;
                PriceSP.Visibility = Visibility.Visible;


                ModifySuitNameSP.Visibility = Visibility.Collapsed;
                ModifyPriceSP.Visibility = Visibility.Collapsed;


                SaveChangesButton.Visibility = Visibility.Collapsed;
                CancelchangesButton.Visibility = Visibility.Collapsed;
                ModifyButton.Visibility = Visibility.Visible;
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试。");
            }
        }
        private void UploadPictureButtonClick(object sender, RoutedEventArgs e)
        {
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

            try
            {
                AvatarImage.Source = new BitmapImage(new Uri(fileName, UriKind.RelativeOrAbsolute));
                GoodsService gS = ServiceFactory.GetGoodsService();
                if (gS.UpdatePhoto(SourceManager.SellerCurSuit.photo_id, fileName))
                {
                    SourceManager.SellerSuits = new List<Suit>();
                    System.Windows.MessageBox.Show("Upload Compelete!");
                }
                else
                {
                    System.Windows.MessageBox.Show("Upload Compelete!");
                }
            }
            catch
            {
                System.Windows.MessageBox.Show("Upload Failed!");
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



        public BitmapImage ByteArrayToBitmapImage(byte[] byteArray)
        {
            BitmapImage bmp = null;

            try
            {
                bmp = new BitmapImage();
                bmp.BeginInit();
                bmp.StreamSource = new MemoryStream(byteArray);
                bmp.EndInit();
            }
            catch
            {
                bmp = null;
            }

            return bmp;
        }  
    }
}
