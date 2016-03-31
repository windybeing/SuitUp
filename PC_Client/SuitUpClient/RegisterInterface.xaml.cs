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
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.Util;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Chpoi.SuitUp.Source;

namespace chpoi.suitup.ui
{
    /// <summary>
    /// RegisterInterface.xaml 的交互逻辑
    /// </summary>
    public partial class RegisterInterface : Window
    {
        public RegisterInterface()
        {
            InitializeComponent();
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
        private void AddUserButtonClick(object sender, RoutedEventArgs e)
        {

            //注册用户
            try
            {
                string username = userNameTextBox.Text;
                string password = userPasswordBox.Password;
                string comfirmPassword = UserConfirmPasswordbox.Password;
                string userEmail = UserEmailTextBox.Text;
                string phoneNumber = UserPhoneTextBox.Text;
                char[] illegalCharacter = { '?', '"', '<', '>', '/', '\\', '|', ':', '*' };
                char[] emailCharacter = { '@', '.'};
                if (username == "" || password == "" || comfirmPassword == "")
                {
                    MessageBox.Show("用户名密码不能为空");
                    return;
                }
                //todo email
                if (userEmail == "")
                {
                    MessageBox.Show("邮箱不能为空");
                    return;
                }

                if (phoneNumber == "")
                {
                    MessageBox.Show("手机号码不能为空");
                    return;
                }
                try
                {
                    int.Parse(phoneNumber);
                }
                catch
                {
                    MessageBox.Show("手机号码不合法");
                    return;
                }

                if(userEmail.IndexOf('@') == -1){
                    MessageBox.Show("邮箱不合法");
                    return;
                }
                if (userEmail.IndexOf('.') == -1)
                {
                    MessageBox.Show("邮箱不合法");
                    return;
                }
                if (username.IndexOfAny(illegalCharacter) != -1 || password.IndexOfAny(illegalCharacter) != -1 || comfirmPassword.IndexOfAny(illegalCharacter) != -1 || userEmail.IndexOfAny(illegalCharacter) != -1 || phoneNumber.IndexOfAny(illegalCharacter) != -1)
                {
                    MessageBox.Show("含有非法字符串\\ /:*?\"<>|");
                    return;
                }
                if (userPasswordBox.Password != UserConfirmPasswordbox.Password)
                {
                    MessageBox.Show("密码不一致");
                    return;
                }
                ClientService cs = ServiceFactory.GetClientService();
                string retMessage = cs.Register(username, password, userEmail, phoneNumber);
                if (cs.IsSuccess(retMessage))
                {
                    cs.GetUserInfor(retMessage);
                    MessageBox.Show("Register Success!");
                    GoodsWindow gW = new GoodsWindow();
                    gW.Show();
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
                    SourceManager.ErrorMessage = "";
                    return;
                }
            }
            catch
            {
                MessageBox.Show("注册错误！");
            }
        }
        private void BackButtonClick(object sender, RoutedEventArgs e)
        {
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }
    }
}
