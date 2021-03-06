﻿using System;
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
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.IO;
using Microsoft.Win32;
using Chpoi.SuitUp.Util;
using Chpoi.SuitUp.SSL;
using Chpoi.SuitUp.Source;
using Chpoi.SuitUp.Entity;
using Chpoi.SuitUp.Service;



namespace chpoi.suitup.ui
{
    public partial class LoginWindow : Window
    {
        public LoginWindow()
        {
            try
            {
                InitializeComponent();
                //初始化信息
                SourceManager.client = new Client();
                SourceManager.shoppingcart = new ShoppingCart();
                SourceManager.order = new Order();
                SourceManager.seller = new Seller();

                SourceManager.SellerSuits = new List<Suit>();
                SourceManager.suits = new List<Suit>();
                SourceManager.end = false;
                SourceManager.pagemax = -1;
                SourceManager.sellerSuitEnd = false;
                SourceManager.sellerSuitPageMax = -1;

                SourceManager.clientorder = new List<ClientOrder>();
                SourceManager.sellerorder = new List<SellerOrder>();
                SourceManager.clientOrderEnd = false;
                SourceManager.sellerOrderEnd = false;
                SourceManager.sellerOrderPageMax = -1;
                SourceManager.clientOrderPageMax = -1;

                SourceManager.curOrder = new ClientOrder();
                SourceManager.sellerCurOrder = new SellerOrder();

                SourceManager.SellerCurSuit = new Suit();
                SourceManager.sellerNewPicPath = "";

                SourceManager.UserIsSearch = false;
                SourceManager.UserKeyword = "";
                SourceManager.UserSearchSuits = new List<Suit>();
                SourceManager.UserSearchEnd = false;
                SourceManager.UserSearchPageMax = -1;
                SourceManager.ErrorMessage = "";
            }
            catch
            {
                LoginWindow lW = new LoginWindow();
                lW.Show();
                this.Close();
                return;
            }
        }

        private void LoginButtonClick(object sender, RoutedEventArgs e)
        {
            //判断登录信息
            try
            {
                string username = userNameTextBox.Text;
                string password = userPasswordBox.Password;
                char[] illegalCharacter = { '?', '"', '<', '>', '/', '\\', '|', ':', '*' };
                if (username == "" || password == "")
                {
                    MessageBox.Show("用户名密码不能为空");
                    return;
                }


                if (username.IndexOfAny(illegalCharacter) != -1 || password.IndexOfAny(illegalCharacter) != -1)
                {
                    MessageBox.Show("含有非法字符串\\ /:*?\"<>|");
                    return;
                }

                ClientService cs = ServiceFactory.GetClientService();
                string retMessage = cs.Login(username, password);
                if (retMessage == "-1")
                {
                    MessageBox.Show("网络错误，请稍后再试");
                    LoginWindow lW = new LoginWindow();
                    lW.Show();
                    this.Close();
                    return;
                }
                if (cs.IsSuccess(retMessage))
                {
                    if (MessageBox.Show("登录成功。", "登录", MessageBoxButton.YesNo, MessageBoxImage.Information) == MessageBoxResult.Yes)
                    {
                        cs.GetUserInfor(retMessage);

                    }
                    GoodsWindow gW = new GoodsWindow();
                    gW.Show();
                    this.Close();
                    return;
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
            catch
            {
                MessageBox.Show("系统错误，请稍后再试");
                LoginWindow lW = new LoginWindow();
                lW.Show();
                this.Close();
                return;
            }
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

        private void RegisterButtonClick(object sender, RoutedEventArgs e)
        {
                RegisterInterface rI = new RegisterInterface();
                rI.Show();
                this.Close();
        }
        private void BuyerButtonClick(object sender, RoutedEventArgs e)
        {
        }
        private void SellerButtonClick(object sender, RoutedEventArgs e)
        {
                SellerLoginInterface sI = new SellerLoginInterface();
                sI.Show();
                this.Close();
        }
    }
}
