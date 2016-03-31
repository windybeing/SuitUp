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
    /// Interaction logic for ListsWindow.xaml
    /// </summary>
    /// 

    public partial class UserDetailOrderInterface : Window
    {
        int page = 0;
        public UserDetailOrderInterface()
        {
            try
            {
                //显示订单详细信息
                InitializeComponent();
                AddressTextBlock.Text = SourceManager.curOrder.address;
                ReceiverTextBlock.Text = SourceManager.curOrder.receiver;
                AmountTextBlock.Text = SourceManager.curOrder.amount.ToString();
                OrderNumberTextBlock.Text = SourceManager.curOrder._id;
                PhoneNumberTextBlock.Text = SourceManager.curOrder.phonenumber;
                TimeTextBlock.Text = SourceManager.curOrder.time;
                WaistTextBlock.Text = SourceManager.curOrder.waistline.ToString();
                HipsTextBlock.Text = SourceManager.curOrder.hipline.ToString();
                BustTextBlock.Text = SourceManager.curOrder.bust.ToString();
                ShoulderTextBlock.Text = SourceManager.curOrder.shoulder.ToString();
                ForebreastTextBlock.Text = SourceManager.curOrder.forebreast.ToString();
                MetathoraxTextBlock.Text = SourceManager.curOrder.metathorax.ToString();
                UpperlimbTextBlock.Text = SourceManager.curOrder.upperlimb.ToString();
                LowerlimbTextBlock.Text = SourceManager.curOrder.lowerlimb.ToString();
                Lst.ItemsSource = SourceManager.curOrder.orderItems;
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试。");
            }
       
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
        private void SearchButtonOnClick(object sender, RoutedEventArgs e)
        {

        }
        private void Button1_Click(object sender, RoutedEventArgs e)
        {

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
        private void DetailButtonClick(object sender, RoutedEventArgs e)
        {
        }
        private void OrderButtonClick(object sender, RoutedEventArgs e)
        {
        }

    }
}
