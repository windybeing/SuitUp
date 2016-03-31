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
using Chpoi.SuitUp.Util;
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.Source;
using Chpoi.Suitup;

namespace chpoi.suitup.ui
{
    /// <summary>
    /// Interaction logic for BuyerInterfaceBodyParameters.xaml
    /// </summary>
    public partial class BuyerInterfaceBodyParameters  : Window
    {
        public BuyerInterfaceBodyParameters()
        {
            InitializeComponent();
        }

        private void BuyerInformationButtonClick(object sender, RoutedEventArgs e)
        {
            BuyerWindow bW = new BuyerWindow();
            bW.Show();
            this.Close();
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

            try
            {
                 //显示信息
                InputBust.Text = BustTextBlock.Text;
                InputWaist.Text = WaistTextBlock.Text;
                InputHips.Text = HipsTextBlock.Text;
                InputShoulder.Text = ShoulderTextBlock.Text;
                InputForebreast.Text = ForebreastTextBlock.Text;
                InputMetathorax.Text = MetathoraxTextBlock.Text;
                InputUpperlimb.Text = UpperlimbTextBlock.Text;
                InputLowerlimb.Text = LowerlimbTextBlock.Text;

                //切换到可编辑页面
                ModifyBustSp.Visibility = Visibility.Visible;
                BustSp.Visibility = Visibility.Collapsed;
                ModifyWaistSp.Visibility = Visibility.Visible;
                WaistSp.Visibility = Visibility.Collapsed;
                ModifyHipsSp.Visibility = Visibility.Visible;
                HipsSp.Visibility = Visibility.Collapsed;

                ModifyShoulderSp.Visibility = Visibility.Visible;
                ShoulderSp.Visibility = Visibility.Collapsed;
                ModifyForebreastSp.Visibility = Visibility.Visible;
                ForebreastSp.Visibility = Visibility.Collapsed;
                ModifyMetathoraxSp.Visibility = Visibility.Visible;
                MetathoraxSp.Visibility = Visibility.Collapsed;
                ModifyUpperlimbSp.Visibility = Visibility.Visible;
                UpperlimbSp.Visibility = Visibility.Collapsed;
                ModifyLowerlimbSp.Visibility = Visibility.Visible;
                LowerlimbSp.Visibility = Visibility.Collapsed;


                ModifyButton.Visibility = Visibility.Collapsed;
                SaveButton.Visibility = Visibility.Collapsed;
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
        private void SaveButtonClick(object sender, RoutedEventArgs e)
        {
            //验证三围信息
            try
            {
                double.Parse(BustTextBlock.Text);
                double.Parse(WaistTextBlock.Text);
                double.Parse(HipsTextBlock.Text);
                double.Parse(ShoulderTextBlock.Text);
                double.Parse(ForebreastTextBlock.Text);
                double.Parse(MetathoraxTextBlock.Text);
                double.Parse(UpperlimbTextBlock.Text);
                double.Parse(LowerlimbTextBlock.Text);
            }
            catch
            {
                System.Windows.MessageBox.Show("身体参数不合法！");
                return;
            }
            //保存三围信息到内存
            try
            {
                SourceManager.client.bust = double.Parse(BustTextBlock.Text);
                SourceManager.client.waistline = double.Parse(WaistTextBlock.Text);
                SourceManager.client.hipline = double.Parse(HipsTextBlock.Text);
                SourceManager.client.shoulder = double.Parse(ShoulderTextBlock.Text);
                SourceManager.client.forebreast = double.Parse(ForebreastTextBlock.Text);
                SourceManager.client.metathorax = double.Parse(MetathoraxTextBlock.Text);
                SourceManager.client.upperlimb = double.Parse(UpperlimbTextBlock.Text);
                SourceManager.client.lowerlimb = double.Parse(LowerlimbTextBlock.Text);
            }
            catch
            {
                System.Windows.MessageBox.Show("保存信息错误！");
                return;
            }
        }
        private void SaveChangesButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
                //保存三围信息，并显示出来
                BustTextBlock.Text = InputBust.Text;
                WaistTextBlock.Text = InputWaist.Text;
                HipsTextBlock.Text = InputHips.Text;
                ShoulderTextBlock.Text = InputShoulder.Text;
                ForebreastTextBlock.Text = InputForebreast.Text;
                MetathoraxTextBlock.Text = InputMetathorax.Text;
                UpperlimbTextBlock.Text = InputUpperlimb.Text;
                LowerlimbTextBlock.Text = InputLowerlimb.Text;


                ModifyBustSp.Visibility = Visibility.Collapsed;
                BustSp.Visibility = Visibility.Visible;
                ModifyWaistSp.Visibility = Visibility.Collapsed;
                WaistSp.Visibility = Visibility.Visible;
                ModifyHipsSp.Visibility = Visibility.Collapsed;
                HipsSp.Visibility = Visibility.Visible;
                ModifyShoulderSp.Visibility = Visibility.Collapsed;
                ShoulderSp.Visibility = Visibility.Visible;
                ModifyForebreastSp.Visibility = Visibility.Collapsed;
                ForebreastSp.Visibility = Visibility.Visible;
                ModifyMetathoraxSp.Visibility = Visibility.Collapsed;
                MetathoraxSp.Visibility = Visibility.Visible;
                ModifyUpperlimbSp.Visibility = Visibility.Collapsed;
                UpperlimbSp.Visibility = Visibility.Visible;
                ModifyLowerlimbSp.Visibility = Visibility.Collapsed;
                LowerlimbSp.Visibility = Visibility.Visible;

                SaveChangesButton.Visibility = Visibility.Collapsed;
                CancelchangesButton.Visibility = Visibility.Collapsed;
                ModifyButton.Visibility = Visibility.Visible;
                SaveButton.Visibility = Visibility.Visible;
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
        //取消按钮
        private void CancelChangesButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
                ModifyBustSp.Visibility = Visibility.Collapsed;
                BustSp.Visibility = Visibility.Visible;
                ModifyWaistSp.Visibility = Visibility.Collapsed;
                WaistSp.Visibility = Visibility.Visible;
                ModifyHipsSp.Visibility = Visibility.Collapsed;
                HipsSp.Visibility = Visibility.Visible;
                ModifyShoulderSp.Visibility = Visibility.Collapsed;
                ShoulderSp.Visibility = Visibility.Visible;
                ModifyForebreastSp.Visibility = Visibility.Collapsed;
                ForebreastSp.Visibility = Visibility.Visible;
                ModifyMetathoraxSp.Visibility = Visibility.Collapsed;
                MetathoraxSp.Visibility = Visibility.Visible;
                ModifyUpperlimbSp.Visibility = Visibility.Collapsed;
                UpperlimbSp.Visibility = Visibility.Visible;
                ModifyLowerlimbSp.Visibility = Visibility.Collapsed;
                LowerlimbSp.Visibility = Visibility.Visible;



                SaveChangesButton.Visibility = Visibility.Collapsed;
                CancelchangesButton.Visibility = Visibility.Collapsed;
                ModifyButton.Visibility = Visibility.Visible;
                SaveButton.Visibility = Visibility.Visible;
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

        private void UploadFrontPhotoButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
            //上传正面照片
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "All Image Files|*.bmp;*.ico;*.gif;*.jpeg;*.jpg;*.png;*.tif;*.tiff|"  +
"Windows Bitmap(*.bmp)|*.bmp|"  +
"Windows Icon(*.ico)|*.ico|"  +
"Graphics Interchange Format (*.gif)|(*.gif)|"  +
"JPEG File Interchange Format (*.jpg)|*.jpg;*.jpeg|"  +
"Portable Network Graphics (*.png)|*.png|"  +
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

                FontPhoto.Source = new BitmapImage(new Uri(fileName, UriKind.RelativeOrAbsolute));
                SourceManager.client.frontphotopath = fileName;
            }
            catch
            {
                System.Windows.MessageBox.Show("上传错误。");
            }
        }
        private void UploadSidePhotoButtonClick(object sender, RoutedEventArgs e)
        {
            //上传侧面照片

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
                SidePhoto.Source = new BitmapImage(new Uri(fileName, UriKind.RelativeOrAbsolute));
                SourceManager.client.sidephotopath = fileName;
            }
            catch
            {
                System.Windows.MessageBox.Show("上传错误。");
            }
        }
       
        private void PersonalInforButtonClick(object sender, RoutedEventArgs e)
        {

            BuyerWindow bW = new BuyerWindow();
            bW.Show();   
            this.Close();
        }
        private void BodyParametersClick(object sender, RoutedEventArgs e)
        {

        }
        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            //注销
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }
        //3d扫描
        private void ScanButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
                try
                {
                    ScanService sS = ServiceFactory.GetScanService();
                    sS.scan();
                }
                catch
                {
                    System.Windows.MessageBox.Show("三维扫描错误，请稍后重试");
                    return;
                }
                WaistTextBlock.Text = SourceManager.client.waistline.ToString();
                HipsTextBlock.Text = SourceManager.client.hipline.ToString();
                BustTextBlock.Text = SourceManager.client.bust.ToString();
                ShoulderTextBlock.Text = SourceManager.client.shoulder.ToString();
                ForebreastTextBlock.Text = SourceManager.client.forebreast.ToString();
                MetathoraxTextBlock.Text = SourceManager.client.metathorax.ToString();
                UpperlimbTextBlock.Text = SourceManager.client.upperlimb.ToString();
                LowerlimbTextBlock.Text = SourceManager.client.lowerlimb.ToString();
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
    }
}
