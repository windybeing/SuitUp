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
using System.Windows.Shapes;
using System.Data;
using MaterialDesignColors.WpfExample.Domain;
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.Util;
using Chpoi.SuitUp.SSL;
using Chpoi.SuitUp.Entity;
using Chpoi.SuitUp.Source;

namespace chpoi.suitup.ui
{
    /// <summary>
    /// Interaction logic for SellerDetailOrderInterface.xaml
    /// </summary>
    /// 

    public partial class SellerDetailOrderInterface : Window
    {
        int page = 0;
        public SellerDetailOrderInterface()
        {
            try
            {
                //初始化信息
                InitializeComponent();
                ReceiverTextBlock.Text = SourceManager.sellerCurOrder.receiver;
                AmountTextBlock.Text = SourceManager.sellerCurOrder.amount.ToString();
                OrderNumberTextBlock.Text = SourceManager.sellerCurOrder._id;
                PhoneNumberTextBlock.Text = SourceManager.sellerCurOrder.phonenumber;
                TimeTextBlock.Text = SourceManager.sellerCurOrder.time;
                WaistTextBlock.Text = SourceManager.sellerCurOrder.waistline.ToString();
                HipsTextBlock.Text = SourceManager.sellerCurOrder.hipline.ToString();
                BustTextBlock.Text = SourceManager.sellerCurOrder.bust.ToString();
                ShoulderTextBlock.Text = SourceManager.sellerCurOrder.shoulder.ToString();
                ForebreastTextBlock.Text = SourceManager.sellerCurOrder.forebreast.ToString();
                MetathoraxTextBlock.Text = SourceManager.sellerCurOrder.metathorax.ToString();
                UpperlimbTextBlock.Text = SourceManager.sellerCurOrder.upperlimb.ToString();
                LowerlimbTextBlock.Text = SourceManager.sellerCurOrder.lowerlimb.ToString();
                Lst.ItemsSource = SourceManager.sellerCurOrder.orderItems;
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试。");
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


        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            //注销
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }

    }
}
