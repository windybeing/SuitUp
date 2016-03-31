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

    public partial class SellerOrderWindow : Window
    {
        int page = 0;
        public SellerOrderWindow()
        {
            try
            {
                //显示订单信息
                InitializeComponent();

                if (SourceManager.sellerOrderEnd == false)
                {
                    try
                    {
                        if (SourceManager.sellerorder[0] == null) { }
                        List<SellerOrder> orderLst = new List<SellerOrder>();
                        for (int i = page * 10; i < page * 10 + 10; i++)
                        {
                            orderLst.Add(SourceManager.sellerorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                    catch
                    {
                        OrderService os = ServiceFactory.GetOrderService();
                        int t = os.SellerGetOrderInfor(SourceManager.seller._id, 0, 10);
                        if (t < 10)
                        {
                            SourceManager.sellerOrderEnd = true;
                            SourceManager.sellerOrderPageMax = page;
                            SourceManager.sellerOrderLastPageCount = t;
                        }
                        List<SellerOrder> orderLst = new List<SellerOrder>();
                        for (int i = 0; i < t; i++)
                        {
                            orderLst.Add(SourceManager.sellerorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                }
                else
                {

                    if (SourceManager.sellerOrderPageMax == 0)
                    {
                        List<SellerOrder> orderLst = new List<SellerOrder>();
                        for (int i = 0; i < SourceManager.sellerOrderLastPageCount; i++)
                        {
                            orderLst.Add(SourceManager.sellerorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                    else
                    {
                        List<SellerOrder> orderLst = new List<SellerOrder>();
                        for (int i = 0; i < 10; i++)
                        {
                            orderLst.Add(SourceManager.sellerorder[i]);
                        }
                        Lst.ItemsSource = orderLst;
                    }
                }
            }
            catch
            {
                MessageBox.Show("载入订单失败，请稍后再试。");
            }
        }

        private void OrderManageButtonClick(object sender, RoutedEventArgs e)
        {
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
       
        private void DetailButtonClick(object sender, RoutedEventArgs e)
        {
            SellerOrder curItem = (SellerOrder)((ListBoxItem)Lst.ContainerFromElement((Button)sender)).Content;
            SourceManager.sellerCurOrder = curItem;
            SellerDetailOrderInterface sDOI = new SellerDetailOrderInterface();
            sDOI.Show();
            this.Close();
        }

        private void NextPageButtonClick(object sender, RoutedEventArgs e)
        {
            try{
                if (page == SourceManager.sellerOrderPageMax)
                {
                    MessageBox.Show("已是最后一页。");
                    return;
                }
            //翻页
            page++;
            try
            {
                if (SourceManager.sellerorder[page * 10] == null) { }
                List<SellerOrder> orderLst = new List<SellerOrder>();
                if(page == SourceManager.sellerOrderPageMax){
                    for (int i = page * 10; i < page * 10 + SourceManager.sellerOrderLastPageCount; i++)
                    {
                        orderLst.Add(SourceManager.sellerorder[i]);
                    }
                    Lst.ItemsSource = orderLst;
                    return;
                }
                for (int i = page * 10; i < page * 10 + 10; i++)
                {
                    orderLst.Add(SourceManager.sellerorder[i]);
                }
                Lst.ItemsSource = orderLst;
            }
            catch
            {
                OrderService os = ServiceFactory.GetOrderService();
                int t = os.ClientGetOrderInfor(SourceManager.seller._id, 0, 10);
                if (t == 0)
                {
                    MessageBox.Show("已经是最后一页");
                    return;
                }
                if (t != 10)
                {
                    SourceManager.sellerOrderEnd = true;
                    SourceManager.sellerOrderPageMax = page;
                    SourceManager.sellerOrderLastPageCount = t;
                }
                List<SellerOrder> orderLst = new List<SellerOrder>();
                for (int i = page * 10; i < page * 10 + t; i++)
                {
                    orderLst.Add(SourceManager.sellerorder[i]);
                }
                Lst.ItemsSource = orderLst;
            }
            }
            catch
            {
                MessageBox.Show("载入订单失败，请稍后再试。");
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
                List<SellerOrder> orderLst = new List<SellerOrder>();
                for (int i = page * 10; i < page * 10 + 10; i++)
                {
                    orderLst.Add(SourceManager.sellerorder[i]);
                }
                Lst.ItemsSource = orderLst;
            }
            catch
            {
                MessageBox.Show("载入订单失败，请稍后再试。");
            }
        }
    }
}
