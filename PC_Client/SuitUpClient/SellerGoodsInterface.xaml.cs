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
    /// Interaction logic for SellerGoodsWindow.xaml
    /// </summary>
    /// 

    public partial class SellerGoodsWindow : Window
    {
        int page = 0;
        public SellerGoodsWindow()
        {
            try
            {
                //初始化信息
                InitializeComponent();
                SourceManager.SellerCurSuit = new Suit();
                GoodsService gS = ServiceFactory.GetGoodsService();
                if (SourceManager.sellerSuitEnd == false)
                {
                    try
                    {
                        if (SourceManager.SellerSuits[0] == null) { }
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = page * 10; i < page * 10 + 10; i++)
                        {
                            goodsLst.Add(SourceManager.SellerSuits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                    catch
                    {
                        int t = gS.SellerGetGoodsInfor(0, 10, SourceManager.seller._id);
                        for (int i = 0; i < t; i++)
                        {
                            SourceManager.SellerSuits[i].photo = gS.GetGoodsPhoto(SourceManager.SellerSuits[i].photo_id);
                        }
                        if (t < 10)
                        {
                            SourceManager.sellerSuitEnd = true;
                            SourceManager.sellerSuitPageMax = page;
                            SourceManager.sellerLastPageCount = t;
                        }
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = 0; i < t; i++)
                        {
                            goodsLst.Add(SourceManager.SellerSuits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                }
                else
                {
                    if (SourceManager.sellerSuitPageMax == 0)
                    {
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = 0; i < SourceManager.sellerLastPageCount; i++)
                        {
                            goodsLst.Add(SourceManager.SellerSuits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                    else
                    {
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = 0; i < 10; i++)
                        {
                            goodsLst.Add(SourceManager.SellerSuits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                }
            }
            catch
            {
                System.Windows.MessageBox.Show("载入商品失败，请稍后再试");
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


        private void NextPageButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
                if (page == SourceManager.sellerSuitPageMax)
                {
                    MessageBox.Show("已是最后一页");
                    return;
                }
                //翻页
                page++;
                try
                {
                    if (SourceManager.SellerSuits[page * 10] == null) { }
                    List<Suit> goodsLst = new List<Suit>();
                    if (page == SourceManager.sellerSuitPageMax)
                    {
                        for (int i = 0; i < page * 10 + SourceManager.sellerLastPageCount; i++)
                        {
                            goodsLst.Add(SourceManager.SellerSuits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                        return;
                    }
                    for (int i = page * 10; i < page * 10 + 10; i++)
                    {
                        goodsLst.Add(SourceManager.SellerSuits[i]);
                    }
                    Lst.ItemsSource = goodsLst;
                }
                catch
                {
                    GoodsService gS = ServiceFactory.GetGoodsService();
                    int t = gS.SellerGetGoodsInfor(page, 10, SourceManager.seller._id);
                    if (t == 0)
                    {
                        MessageBox.Show("已经是最后一页");
                        return;
                    }
                    if (t != 10)
                    {
                        SourceManager.sellerSuitEnd = true;
                        SourceManager.sellerSuitPageMax = page;
                        SourceManager.sellerLastPageCount = t;
                    }
                    for (int i = page * 10; i < page * 10 + t; i++)
                    {
                        SourceManager.SellerSuits[i].photo = gS.GetGoodsPhoto(SourceManager.SellerSuits[i].photo_id);
                    }
                    List<Suit> goodsLst = new List<Suit>();
                    for (int i = page * 10; i < page * 10 + t; i++)
                    {
                        goodsLst.Add(SourceManager.SellerSuits[i]);
                    }
                    Lst.ItemsSource = goodsLst;
                }
            }
            catch
            {
                System.Windows.MessageBox.Show("载入订单失败，请稍后再试");
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
                List<Suit> goodsLst = new List<Suit>();
                for (int i = page * 10; i < page * 10 + 10; i++)
                {
                    goodsLst.Add(SourceManager.SellerSuits[i]);
                }
                Lst.ItemsSource = goodsLst;
            }
            catch
            {
                System.Windows.MessageBox.Show("载入订单失败，请稍后再试");
            }
        }
        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            //注销
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }
        //修改商品
        private void ModifyGoodsButtonClick(object sender, RoutedEventArgs e)
        {
            Suit curItem = (Suit)((ListBoxItem)Lst.ContainerFromElement((Button)sender)).Content;
            SourceManager.SellerCurSuit = curItem;
            SellerModifyGoodsInterface sMGI = new SellerModifyGoodsInterface();
            sMGI.Show();
            this.Close();
        }
        //删除商品
        private void DeleteGoodsButtonClick(object sender, RoutedEventArgs e)
        {
            Suit curItem = (Suit)((ListBoxItem)Lst.ContainerFromElement((Button)sender)).Content;
            SourceManager.SellerCurSuit = curItem;
            GoodsService gS = ServiceFactory.GetGoodsService();
            if (gS.DeleteGoods(curItem._id))
            {
                MessageBox.Show("删除成功");
                SourceManager.SellerSuits = new List<Suit>();
                SourceManager.sellerSuitEnd = false;
                SourceManager.sellerSuitPageMax = -1;
                SourceManager.sellerLastPageCount = 0;
                SellerGoodsWindow sGW = new SellerGoodsWindow();
                sGW.Show();
                this.Close();
            }
            else
            {
                if (SourceManager.ErrorMessage[0] == 'E')
                {
                    MessageBox.Show("系统错误，请稍后再试。");
                    LoginWindow lW = new LoginWindow();
                    lW.Show();
                    this.Close();
                    return;
                }
                MessageBox.Show(SourceManager.ErrorMessage);
                return;
            }

        }
        //添加商品
        private void AddGoodsButtonClick(object sender, RoutedEventArgs e)
        {
            SellerAddGoodsInterface sAGI = new SellerAddGoodsInterface();
            sAGI.Show();
            this.Close();
        }
    }
}
