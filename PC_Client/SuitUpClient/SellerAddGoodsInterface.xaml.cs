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
    /// Interaction logic for SellerAddGoodsInterface.xaml
    /// </summary>
    public partial class SellerAddGoodsInterface : Window
    {
        public SellerAddGoodsInterface()
        {
            InitializeComponent();
            SourceManager.sellerNewPicPath = "";
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
        
        private void PersonalInforButtonClick(object sender, RoutedEventArgs e)
        {

        }
        private void BodyParametersClick(object sender, RoutedEventArgs e)
        {
            BuyerInterfaceBodyParameters bIBP = new BuyerInterfaceBodyParameters();
            bIBP.Show(); 
            this.Close();
        }
       
        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            //注销
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }
        private void UploadPictureButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
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
                Picture.Source = new BitmapImage(new Uri(fileName, UriKind.RelativeOrAbsolute));
                SourceManager.sellerNewPicPath = fileName;
            }
            catch
            {
                System.Windows.MessageBox.Show("上传失败。");
            }
        }
        private void AddButtonClick(object sender, RoutedEventArgs e)
        {
            //保存修改后的信息
            try
            {
                string NewSuitName = InputSuitName.Text;
                if (NewSuitName == "")
                {
                    System.Windows.MessageBox.Show("商品名称不能为空！");
                    return;
                }

                double NewPrice;
                if (InputPrice.Text == "")
                {
                    System.Windows.MessageBox.Show("价格不能为空！");
                    return;
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
                if (SourceManager.sellerNewPicPath == "")
                {
                    System.Windows.MessageBox.Show("无法获取图片！");
                    return;
                }
                GoodsService gS = ServiceFactory.GetGoodsService();
                if (!gS.CreateGoods(SourceManager.sellerNewPicPath, NewSuitName, NewPrice, SourceManager.seller._id, SourceManager.seller.manufacturer_id))
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
                SourceManager.sellerLastPageCount = 0;
                SourceManager.sellerSuitEnd = false;
                SourceManager.sellerSuitPageMax = -1;
                System.Windows.MessageBox.Show("添加商品成功。");
            }
            catch
            {
                System.Windows.MessageBox.Show("添加商品失败！");
                return;
            }
        }
    }
}
