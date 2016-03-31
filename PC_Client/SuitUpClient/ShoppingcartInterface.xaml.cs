using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using Chpoi.SuitUp.Entity;
using Chpoi.SuitUp.Source;

namespace chpoi.suitup.ui
{
    /// <summary>
    /// ShoppingcartInterface.xaml 的交互逻辑
    /// </summary>
    public partial class ShoppingcartInterface : Window
    {
        public ShoppingcartInterface()
        {
            try
            {
                InitializeComponent();
                //初始化购物车信息
                Lst.ItemsSource = SourceManager.shoppingcart.ShoppingCartItems;
                double dTotPrice = 0;
                for (int i = 0; i < SourceManager.shoppingcart.ShoppingCartItems.Count; i++)
                {
                    dTotPrice += SourceManager.shoppingcart.ShoppingCartItems[i].price * SourceManager.shoppingcart.ShoppingCartItems[i].number;
                }
                totalPrice.Text = "总计：" + dTotPrice.ToString();
                SourceManager.shoppingcart.amount = dTotPrice;
            }
            catch
            {
                MessageBox.Show("载入购物车商品失败，请稍后再试！");
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

        private void BuyButtonClick(object sender, RoutedEventArgs e)
        {
            SourceManager.order.amount = SourceManager.shoppingcart.amount;
            UserConfirmOrder uCO = new UserConfirmOrder();
            uCO.Show();
            this.Close();
        }
        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }
        private void RemoveItemButtonClick(object sender, RoutedEventArgs e)
        {
            //删除购物车中条目
            try
            {
                ShoppingCartItem curItem = (ShoppingCartItem)((ListBoxItem)Lst.ContainerFromElement((Button)sender)).Content;
                int temp = SourceManager.shoppingcart.ShoppingCartItems.FindIndex((ShoppingCartItem SCI) => SCI.suitname == curItem.suitname);
                SourceManager.shoppingcart.ShoppingCartItems.RemoveAt(temp);
                ShoppingcartInterface sI = new ShoppingcartInterface();
                sI.Show();
                this.Close();

            }
            catch
            {
                MessageBox.Show("删除购物车错误！");
            }
        }
    }
}
