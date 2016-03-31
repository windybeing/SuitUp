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
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.Util;

namespace chpoi.suitup.ui
{
    /// <summary>
    /// UserConfirmOrder.xaml 的交互逻辑
    /// </summary>
    public partial class UserConfirmOrder : Window
    {
        public UserConfirmOrder()
        {
            try
            {
                //显示订单详细信息
                InitializeComponent();

                AddressTextBlock.Text = SourceManager.client.address;
                PhoneNumberTextBlock.Text = SourceManager.client.phonenumber;
                AmountTextBlock.Text = SourceManager.order.amount.ToString();
                WaistTextBlock.Text = SourceManager.client.waistline.ToString();
                HipsTextBlock.Text = SourceManager.client.hipline.ToString();
                BustTextBlock.Text = SourceManager.client.bust.ToString();
                ShoulderTextBlock.Text = SourceManager.client.shoulder.ToString();
                ForebreastTextBlock.Text = SourceManager.client.forebreast.ToString();
                MetathoraxTextBlock.Text = SourceManager.client.metathorax.ToString();
                UpperlimbTextBlock.Text = SourceManager.client.upperlimb.ToString();
                LowerlimbTextBlock.Text = SourceManager.client.lowerlimb.ToString();
            }
            catch
            {
                MessageBox.Show("载入订单失败，请稍后再试。");
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

        }
        private void LogOutButtonClick(object sender, RoutedEventArgs e)
        {
            LoginWindow lW = new LoginWindow();
            lW.Show();
            this.Close();
        }

        private void ModifyButtonClick(object sender, RoutedEventArgs e)
        {
            //切换到可编辑页面
            try
            {
                InputAddress.Text = AddressTextBlock.Text;
                InputBust.Text = BustTextBlock.Text;
                InputHips.Text = HipsTextBlock.Text;
                InputPhoneNumber.Text = PhoneNumberTextBlock.Text;
                InputRecipient.Text = RecipientTextBlock.Text;
                InputShoulder.Text = ShoulderTextBlock.Text;
                InputWaist.Text = WaistTextBlock.Text;
                InputForebreast.Text = ForebreastTextBlock.Text;
                InputMetathorax.Text = MetathoraxTextBlock.Text;
                InputUpperlimb.Text = UpperlimbTextBlock.Text;
                InputLowerlimb.Text = LowerlimbTextBlock.Text;

                AddressSp.Visibility = Visibility.Collapsed;
                ModifyAddressSp.Visibility = Visibility.Visible;
                PhoneNumberSp.Visibility = Visibility.Collapsed;
                ModifyPhoneNumberSp.Visibility = Visibility.Visible;
                RecipientSp.Visibility = Visibility.Collapsed;
                ModifyRecipientSp.Visibility = Visibility.Visible;

                ModifyButton.Visibility = Visibility.Collapsed;
                PayButton.Visibility = Visibility.Collapsed;

                ModifyBustSp.Visibility = Visibility.Visible;
                BustSp.Visibility = Visibility.Collapsed;
                ModifyWaistSp.Visibility = Visibility.Visible;
                WaistSp.Visibility = Visibility.Collapsed;
                ModifyHipsSp.Visibility = Visibility.Visible;
                HipsSp.Visibility = Visibility.Collapsed;

                ModifyShoulderSp.Visibility = Visibility.Visible;
                ShoulderSp.Visibility = Visibility.Collapsed;
                ModifyForebreastSp.Visibility = Visibility.Visible;
                ForebreastSp.Visibility = Visibility.Collapsed;
                ModifyMetathoraxSp.Visibility = Visibility.Visible;
                MetathoraxSp.Visibility = Visibility.Collapsed;
                ModifyUpperlimbSp.Visibility = Visibility.Visible;
                UpperlimbSp.Visibility = Visibility.Collapsed;
                ModifyLowerlimbSp.Visibility = Visibility.Visible;
                LowerlimbSp.Visibility = Visibility.Collapsed;


                SaveChangesButton.Visibility = Visibility.Visible;
                CancelchangesButton.Visibility = Visibility.Visible;
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试。");
                LoginWindow lW = new LoginWindow();
                lW.Show();
                this.Close();
                return;
            }
        }
        private void CancelChangesButtonClick(object sender, RoutedEventArgs e)
        {
            try
            {
                ModifyAddressSp.Visibility = Visibility.Collapsed;
                AddressSp.Visibility = Visibility.Visible;
                ModifyPhoneNumberSp.Visibility = Visibility.Collapsed;
                PhoneNumberSp.Visibility = Visibility.Visible;
                ModifyRecipientSp.Visibility = Visibility.Collapsed;
                RecipientSp.Visibility = Visibility.Visible;

                ModifyBustSp.Visibility = Visibility.Collapsed;
                BustSp.Visibility = Visibility.Visible;
                ModifyWaistSp.Visibility = Visibility.Collapsed;
                WaistSp.Visibility = Visibility.Visible;
                ModifyHipsSp.Visibility = Visibility.Collapsed;
                HipsSp.Visibility = Visibility.Visible;
                ModifyShoulderSp.Visibility = Visibility.Collapsed;
                ShoulderSp.Visibility = Visibility.Visible;
                ModifyForebreastSp.Visibility = Visibility.Collapsed;
                ForebreastSp.Visibility = Visibility.Visible;
                ModifyMetathoraxSp.Visibility = Visibility.Collapsed;
                MetathoraxSp.Visibility = Visibility.Visible;
                ModifyUpperlimbSp.Visibility = Visibility.Collapsed;
                UpperlimbSp.Visibility = Visibility.Visible;
                ModifyLowerlimbSp.Visibility = Visibility.Collapsed;
                LowerlimbSp.Visibility = Visibility.Visible;


                SaveChangesButton.Visibility = Visibility.Collapsed;
                CancelchangesButton.Visibility = Visibility.Collapsed;
                ModifyButton.Visibility = Visibility.Visible;
                PayButton.Visibility = Visibility.Visible;
            }
            catch
            {
                System.Windows.MessageBox.Show("系统错误，请稍后再试。");
                LoginWindow lW = new LoginWindow();
                lW.Show();
                this.Close();
                return;
            }
        }

        private void PayButtonClick(object sender, RoutedEventArgs e)
        {
            //todo
            try
            {
                double.Parse(BustTextBlock.Text);
                double.Parse(WaistTextBlock.Text);
                double.Parse(HipsTextBlock.Text);
                double.Parse(ShoulderTextBlock.Text);
                double.Parse(ForebreastTextBlock.Text);
                double.Parse(MetathoraxTextBlock.Text);
                double.Parse(UpperlimbTextBlock.Text);
                double.Parse(LowerlimbTextBlock.Text);

                if (AddressTextBlock.Text == "")
                {
                    MessageBox.Show("地址不能为空");
                    return;
                }

                if (RecipientTextBlock.Text == "")
                {
                    MessageBox.Show("收件人不能为空");
                    return;
                }
                if (PhoneNumberTextBlock.Text == "")
                {
                    MessageBox.Show("电话不能为空");
                    return;
                }               


                SourceManager.client.bust = double.Parse(BustTextBlock.Text);
                SourceManager.client.waistline = double.Parse(WaistTextBlock.Text);
                SourceManager.client.hipline = double.Parse(HipsTextBlock.Text);
                SourceManager.client.shoulder = double.Parse(ShoulderTextBlock.Text);
                SourceManager.client.forebreast = double.Parse(ForebreastTextBlock.Text);
                SourceManager.client.metathorax = double.Parse(MetathoraxTextBlock.Text);
                SourceManager.client.upperlimb = double.Parse(UpperlimbTextBlock.Text);
                SourceManager.client.lowerlimb = double.Parse(LowerlimbTextBlock.Text);

                SourceManager.order.bust = double.Parse(BustTextBlock.Text);
                SourceManager.order.waistline = double.Parse(WaistTextBlock.Text);
                SourceManager.order.hipline = double.Parse(HipsTextBlock.Text);
                SourceManager.order.shoulder = double.Parse(ShoulderTextBlock.Text);
                SourceManager.order.forebreast = double.Parse(ForebreastTextBlock.Text);
                SourceManager.order.metathorax = double.Parse(MetathoraxTextBlock.Text);
                SourceManager.order.upperlimb = double.Parse(UpperlimbTextBlock.Text);
                SourceManager.order.lowerlimb = double.Parse(LowerlimbTextBlock.Text);
            }
            catch
            {
                System.Windows.MessageBox.Show("身体信息不合法！下单失败");
                return;
            }
            //保存三围信息到内存

            SourceManager.order.address = AddressTextBlock.Text;
            SourceManager.order.phonenumber = PhoneNumberTextBlock.Text;
            SourceManager.order.recipient = RecipientTextBlock.Text;
            OrderService os = ServiceFactory.GetOrderService();
            if (os.PlaceOrder(SourceManager.shoppingcart, SourceManager.client._id, SourceManager.order.address, SourceManager.order.recipient, SourceManager.order.postcode, SourceManager.order.phonenumber,SourceManager.order.bust,SourceManager.order.waistline,SourceManager.order.hipline,SourceManager.order.shoulder,SourceManager.order.forebreast,SourceManager.order.metathorax,SourceManager.order.upperlimb,SourceManager.order.lowerlimb))
            {
                MessageBox.Show("下单成功");
                SourceManager.clientorder = new List<ClientOrder>();
                SourceManager.clientOrderEnd = false;
                SourceManager.clientOrderLastPageCount = 0;
                SourceManager.clientOrderPageMax = -1;
            }
            else
            {
                MessageBox.Show("下单失败");
            }
        }
        private void SaveChangesButtonClick(object sender, RoutedEventArgs e)
        {
            AddressTextBlock.Text = InputAddress.Text;
            PhoneNumberTextBlock.Text = InputPhoneNumber.Text;
            RecipientTextBlock.Text = InputRecipient.Text;
            ModifyAddressSp.Visibility = Visibility.Collapsed;
            AddressSp.Visibility = Visibility.Visible;
            ModifyPhoneNumberSp.Visibility = Visibility.Collapsed;
            PhoneNumberSp.Visibility = Visibility.Visible;
            ModifyRecipientSp.Visibility = Visibility.Collapsed;
            RecipientSp.Visibility = Visibility.Visible;

            BustTextBlock.Text = InputBust.Text;
            WaistTextBlock.Text = InputWaist.Text;
            HipsTextBlock.Text = InputHips.Text;
            ShoulderTextBlock.Text = InputShoulder.Text;
            ForebreastTextBlock.Text = InputForebreast.Text;
            MetathoraxTextBlock.Text = InputMetathorax.Text;
            UpperlimbTextBlock.Text = InputUpperlimb.Text;
            LowerlimbTextBlock.Text = InputLowerlimb.Text;


            ModifyBustSp.Visibility = Visibility.Collapsed;
            BustSp.Visibility = Visibility.Visible;
            ModifyWaistSp.Visibility = Visibility.Collapsed;
            WaistSp.Visibility = Visibility.Visible;
            ModifyHipsSp.Visibility = Visibility.Collapsed;
            HipsSp.Visibility = Visibility.Visible;
            ModifyShoulderSp.Visibility = Visibility.Collapsed;
            ShoulderSp.Visibility = Visibility.Visible;
            ModifyForebreastSp.Visibility = Visibility.Collapsed;
            ForebreastSp.Visibility = Visibility.Visible;
            ModifyMetathoraxSp.Visibility = Visibility.Collapsed;
            MetathoraxSp.Visibility = Visibility.Visible;
            ModifyUpperlimbSp.Visibility = Visibility.Collapsed;
            UpperlimbSp.Visibility = Visibility.Visible;
            ModifyLowerlimbSp.Visibility = Visibility.Collapsed;
            LowerlimbSp.Visibility = Visibility.Visible;


            SaveChangesButton.Visibility = Visibility.Collapsed;
            CancelchangesButton.Visibility = Visibility.Collapsed;
            ModifyButton.Visibility = Visibility.Visible;
            PayButton.Visibility = Visibility.Visible;
        }

        private void GetBodyButton(object sender, RoutedEventArgs e)
        {
            BuyerInterfaceBodyParameters bIBP = new BuyerInterfaceBodyParameters();
            bIBP.Show();
            this.Close();
        }
    }
}
