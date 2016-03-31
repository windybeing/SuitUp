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
    /// Interaction logic for GoodsWindow.xaml
    /// </summary>
    /// 

    public partial class GoodsWindow : Window
    {
        int page = 0;
        public GoodsWindow()
        {
            InitializeComponent();
            //显示商品
            try
            {
                page = 0;

                SourceManager.UserKeyword = "";
                SourceManager.UserSearchSuits = new List<Suit>();
                SourceManager.UserIsSearch = false;
                SourceManager.UserSearchEnd = false;
                SourceManager.UserSearchPageMax = -1;
                SourceManager.clientSearchLastPageCount = -1;

                GoodsService gS = ServiceFactory.GetGoodsService();
                if (SourceManager.end == false)
                {
                    try
                    {
                        if (SourceManager.suits[0] == null) { }
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = page * 10; i < page * 10 + 10; i++)
                        {
                            goodsLst.Add(SourceManager.suits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                    catch
                    {
                        //按页获取商品信息
                        int t = gS.GetGoodsInfor(0, 10);
                        if (t < 10)
                        {
                            SourceManager.sellerSuitEnd = true;
                            SourceManager.sellerSuitPageMax = page;
                            SourceManager.sellerLastPageCount = t;
                        }
                        for (int i = 0; i < t; i++)
                        {
                            SourceManager.suits[i].photo = gS.GetGoodsPhoto(SourceManager.suits[i].photo_id);
                        }
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = 0; i < t; i++)
                        {
                            goodsLst.Add(SourceManager.suits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                }
                else
                {
                    if (SourceManager.pagemax == 0)
                    {
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = 0; i < SourceManager.clientLastPageCount; i++)
                        {
                            goodsLst.Add(SourceManager.suits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                    else
                    {
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = 0; i < 10; i++)
                        {
                            goodsLst.Add(SourceManager.suits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                }
            }
        catch{
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
        //搜索按钮
        private void SearchButtonOnClick(object sender, RoutedEventArgs e)
        {
            try
            {
                page = 0;
                string keyWord = SearchTextBox.Text;
                if (keyWord == "")
                {
                    GoodsWindow gW = new GoodsWindow();
                    gW.Show();
                    this.Close();
                    return;
                }
                SourceManager.UserIsSearch = true;
                SourceManager.UserKeyword = keyWord;
                SourceManager.UserSearchSuits = new List<Suit>();
                GoodsService gS = ServiceFactory.GetGoodsService();
                int t = gS.UserGetGoodsInforByName(keyWord, page, 10);
                if (t < 10)
                {
                    SourceManager.UserSearchEnd = true;
                    SourceManager.UserSearchPageMax = page;
                    SourceManager.clientSearchLastPageCount = t;
                }

                for (int i = 0; i < t; i++)
                {
                    SourceManager.UserSearchSuits[i].photo = gS.GetGoodsPhoto(SourceManager.UserSearchSuits[i].photo_id);
                }
                List<Suit> goodsLst = new List<Suit>();
                for (int i = 0; i < t; i++)
                {
                    goodsLst.Add(SourceManager.UserSearchSuits[i]);
                }
                Lst.ItemsSource = goodsLst;
            }
            catch
            {
                MessageBox.Show("查询失败，请稍后再试。");
            }
        }

        private void AddToShoppingCartButtonClick(object sender, RoutedEventArgs e)
       {
            //将对应项添加到购物车中
           try
           {
               Suit curItem = (Suit)((ListBoxItem)Lst.ContainerFromElement((Button)sender)).Content;
               ShoppingCartItem newSCI = new ShoppingCartItem(curItem.seller_id,curItem.manufacturer_id,curItem._id,curItem.suitname, curItem.price, 1);
               if (SourceManager.shoppingcart.ShoppingCartItems.Exists((ShoppingCartItem SCI) => SCI.suitname == curItem.suitname ? true : false))
               {
                   int temp = SourceManager.shoppingcart.ShoppingCartItems.FindIndex((ShoppingCartItem SCI) => SCI.suitname == curItem.suitname);
                   SourceManager.shoppingcart.ShoppingCartItems[temp].number += 1;
               }
               else
               {
                   SourceManager.shoppingcart.ShoppingCartItems.Add(newSCI);
               }
               MessageBox.Show("添加购物车成功");
           }
           catch
           {
               MessageBox.Show("添加购物车失败！");
           }
        }

        private void NextPageButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
                if (SourceManager.UserIsSearch == false)
                {
                    if (page == SourceManager.pagemax)
                    {
                        MessageBox.Show("已是最后一页。");
                        return;
                    }
                    //翻页
                    page++;
                    try
                    {
                        if (SourceManager.suits[page * 10] == null) { }
                        List<Suit> goodsLst = new List<Suit>();
                        if (page == SourceManager.pagemax)
                        {
                            for (int i = page * 10; i < page * 10 + SourceManager.clientLastPageCount; i++)
                            {
                                goodsLst.Add(SourceManager.suits[i]);
                            }
                            Lst.ItemsSource = goodsLst;
                            return;
                        }
                        for (int i = page * 10; i < page * 10 + 10; i++)
                        {
                            goodsLst.Add(SourceManager.suits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                    catch
                    {
                        GoodsService gS = ServiceFactory.GetGoodsService();
                        int t = gS.GetGoodsInfor(page, 10);
                        if (t == 0)
                        {
                            MessageBox.Show("已经是最后一页");
                            return;
                        }
                        if (t != 10)
                        {
                            SourceManager.end = true;
                            SourceManager.pagemax = page;
                            SourceManager.clientLastPageCount = t;
                         }
                        for (int i = page * 10; i < page * 10 + t; i++)
                        {
                            SourceManager.suits[i].photo = gS.GetGoodsPhoto(SourceManager.suits[i].photo_id);
                        }
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = page * 10; i < page * 10 + t; i++)
                        {
                            goodsLst.Add(SourceManager.suits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                }
                else
                {
                    if (page == SourceManager.UserSearchPageMax)
                    {
                        MessageBox.Show("已经是最后一页");
                        return;
                    }
                    //翻页
                    page++;
                    try
                    {
                        if (SourceManager.UserSearchSuits[page * 10] == null) { }
                        List<Suit> goodsLst = new List<Suit>();
                        if (page == SourceManager.UserSearchPageMax)
                        {
                            for (int i = page * 10; i < page * 10 + SourceManager.clientSearchLastPageCount; i++)
                            {
                                goodsLst.Add(SourceManager.UserSearchSuits[i]);
                            }
                            Lst.ItemsSource = goodsLst;
                            return;
                        }
                        for (int i = page * 10; i < page * 10 + 10; i++)
                        {
                            goodsLst.Add(SourceManager.UserSearchSuits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                    catch
                    {
                        GoodsService gS = ServiceFactory.GetGoodsService();
                        int t = gS.UserGetGoodsInforByName(SourceManager.UserKeyword, page, 10);
                        if (t == 0)
                        {
                            MessageBox.Show("已经是最后一页");
                            return;
                        }
                        if (t != 10)
                        {
                            SourceManager.UserSearchEnd = true;
                            SourceManager.UserSearchPageMax = page;
                            SourceManager.clientSearchLastPageCount = t;
                        }
                        for (int i = page * 10; i < page * 10 + t; i++)
                        {
                            SourceManager.UserSearchSuits[i].photo = gS.GetGoodsPhoto(SourceManager.UserSearchSuits[i].photo_id);
                        }
                        List<Suit> goodsLst = new List<Suit>();
                        for (int i = page * 10; i < page * 10 + t; i++)
                        {
                            goodsLst.Add(SourceManager.UserSearchSuits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                }
            }
            catch
            {
                MessageBox.Show("载入商品错误，请稍后再试。");
            }
        }
        private void PrePageButtonClick(object sender, RoutedEventArgs e)
        {
            //前一页
            try
            {
                if (SourceManager.UserIsSearch == false)
                {
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
                            goodsLst.Add(SourceManager.suits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                    catch
                    {
                        MessageBox.Show("系统错误，请稍后再试！");
                    }
                }
                else
                {
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
                            goodsLst.Add(SourceManager.UserSearchSuits[i]);
                        }
                        Lst.ItemsSource = goodsLst;
                    }
                    catch
                    {
                        MessageBox.Show("系统错误，请稍后再试！");
                    }
                }
            }
            catch
            {
                MessageBox.Show("载入商品失败，请稍后再试。");
            }
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
