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
    /// Interaction logic for UserOrderInterface.xaml
    /// </summary>
    /// 

    public partial class UserOrderInterface : Window
    {
        int page = 0;
        public UserOrderInterface()
        {
            try
            {
                //显示用户订单
                InitializeComponent();

                if (SourceManager.clientOrderEnd == false)
                {
                    try
                    {
                        if (SourceManager.clientorder[0] == null) { }
                        List<ClientOrder> orderLst = new List<ClientOrder>();
                        for (int i = page * 10; i < page * 10 + 10; i++)
                        {
                            orderLst.Add(SourceManager.clientorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                    catch
                    {
                        OrderService os = ServiceFactory.GetOrderService();
                        int t = os.ClientGetOrderInfor(SourceManager.client._id, 0, 10);
                        if (t < 10)
                        {
                            SourceManager.clientOrderEnd = true;
                            SourceManager.clientOrderPageMax = page;
                            SourceManager.clientOrderLastPageCount = t;
                        }
                        List<ClientOrder> orderLst = new List<ClientOrder>();
                        for (int i = 0; i < t; i++)
                        {
                            orderLst.Add(SourceManager.clientorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                }
                else
                {
                    if (SourceManager.clientOrderPageMax == 0)
                    {
                        List<ClientOrder> orderLst = new List<ClientOrder>();
                        for (int i = 0; i < SourceManager.clientOrderLastPageCount; i++)
                        {
                            orderLst.Add(SourceManager.clientorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                    else
                    {
                        List<ClientOrder> orderLst = new List<ClientOrder>();
                        for (int i = 0; i < 10; i++)
                        {
                            orderLst.Add(SourceManager.clientorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                }
            }
            catch
            {
                MessageBox.Show("载入商品失败，请稍后再试。");
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
        private void DetailButtonClick(object sender, RoutedEventArgs e)
        {
            ClientOrder curItem = (ClientOrder)((ListBoxItem)Lst.ContainerFromElement((Button)sender)).Content;
            SourceManager.curOrder = curItem;
            UserDetailOrderInterface uDOI = new UserDetailOrderInterface();
            uDOI.Show();
            this.Close();
        }
        private void OrderButtonClick(object sender, RoutedEventArgs e)
        {
        }

        private void NextPageButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
                if (page == SourceManager.clientOrderPageMax)
                {
                    MessageBox.Show("已是最后一页。");
                    return;
                }
                //翻页
                page++;
                try
                {
                    if (SourceManager.clientorder[page * 10] == null) { }
                    List<ClientOrder> orderLst = new List<ClientOrder>();
                    if (page == SourceManager.clientOrderPageMax)
                    {
                        for (int i = page * 10; i < page * 10 + SourceManager.clientOrderLastPageCount; i++)
                        {
                            orderLst.Add(SourceManager.clientorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                    for (int i = page * 10; i < page * 10 + 10; i++)
                    {
                        orderLst.Add(SourceManager.clientorder[i]);
                    }
                    Lst.ItemsSource = orderLst;
                }
                catch
                {
                    OrderService os = ServiceFactory.GetOrderService();
                    int t = os.ClientGetOrderInfor(SourceManager.client._id, 0, 10);
                    if (t == 0)
                    {
                        MessageBox.Show("已经是最后一页");
                        return;
                    }
                    if (t != 10)
                    {
                        SourceManager.clientOrderEnd = true;
                        SourceManager.clientOrderPageMax = page;
                        SourceManager.clientOrderLastPageCount = t;
                    }
                    List<ClientOrder> orderLst = new List<ClientOrder>();
                    for (int i = page * 10; i < page * 10 + t; i++)
                    {
                        orderLst.Add(SourceManager.clientorder[i]);
                    }
                    Lst.ItemsSource = orderLst;
                }
            }
            catch
            {
                MessageBox.Show("系统错误，请稍后再试！");
            }

        }
        private void PrePageButtonClick(object sender, RoutedEventArgs e)
        {
            //前一页
            try
            {
                page--;
                if (page < 0)
                {
                    MessageBox.Show("已经是第一页");
                    return;
                }
                List<ClientOrder> orderLst = new List<ClientOrder>();
                for (int i = page * 10; i < page * 10 + 10; i++)
                {
                    orderLst.Add(SourceManager.clientorder[i]);
                }
                Lst.ItemsSource = orderLst;
            }
            catch
            {
                MessageBox.Show("系统错误，请稍后再试！");
            }
        }
    }
}
