using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
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
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.Util;
using Chpoi.SuitUp.Source;
using Chpoi.SuitUp.Entity;
using Chpoi.SuitUp.SSL;


namespace chpoi.suitup.ui
{
    /// <summary>
    /// StartWindow.xaml 的交互逻辑
    /// </summary>
    public partial class StartWindow : Window
    {
        //初始页面
        public StartWindow()
        {
            InitializeComponent();
            try
            {
                SslService sS = ServiceFactory.GetSslService();
                sS.RunClient();
            }
            catch
            {
                MessageBox.Show("网络中断，请稍后再试");
            }
            LoginWindow lW = new LoginWindow();;
            lW.Show();
            this.Close();
        }
    }
}
