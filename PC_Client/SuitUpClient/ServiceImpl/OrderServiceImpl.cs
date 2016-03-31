using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Chpoi.SuitUp.SSL;
using Chpoi.SuitUp.Source;
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.Entity;
using Chpoi.SuitUp.Util;

namespace Chpoi.SuitUp.ServiceImpl
{
    class OrderServiceImpl:OrderService
    {
        //下单
        public bool PlaceOrder(ShoppingCart sc, string client_id, string address, string recipient, string postCode, string phoneNumber,double bust,double waistline,double hipline,double shoulder,double forebreast,double metathorax,double upperlimb,double lowerlimb)
        {
            string sendMessage = PlaceOrderGetSendMessage(sc,client_id,address,recipient,postCode,phoneNumber,bust,waistline,hipline,shoulder,forebreast,metathorax,upperlimb,lowerlimb);
            SendMessage(sendMessage);
            return IsPlaceOrder(GetServerMessage());
        }

        private bool IsPlaceOrder(string retMessage)
        {
            JArray jaServerMessage = JArray.Parse(retMessage);
            JObject joretMessage = (JObject)jaServerMessage[0];
            if (joretMessage.GetValue("ret").ToString() == "success")
            {
                return true;
            }
            return false;
        }
        //获得错误信息
        public string GetFailedMessage(string ReturnMessage)
        {
            JArray jaServerMessage = JArray.Parse(ReturnMessage);
            JObject joretMessage = (JObject)jaServerMessage[1];
            return joretMessage.GetValue("message").ToString();
        }

        //发送下订单信息
        private string PlaceOrderGetSendMessage(ShoppingCart sc, string client_id, string address, string recipient, string postCode, string phoneNumber,double bust,double waistline,double hipline,double shoulder,double forebreast,double metathorax,double upperlimb,double lowerlimb)
        {
            string temp = "[{\"function\":\"Purchase\"},{\"client_id\":\"" + client_id + "\",\"address\":\"" + address + "\",\"receiver\":\"" + recipient + "\",\"phonenumber\":\"" + phoneNumber + "\",\"bust\":\"" + bust + "\",\"waistline\":\"" + waistline + "\",\"hipline\":\"" + hipline + "\",\"shoulder\":\"" + shoulder + "\",\"forebreast\":\"" + forebreast + "\",\"metathorax\":\"" + metathorax + "\",\"upperlimb\":\"" + upperlimb + "\",\"lowerlimb\":\"" + lowerlimb + "\",\"type\":\"Client\",\"orderItems\":[";
           
            int length = sc.ShoppingCartItems.Count;

            for (int i = 0; i < length-1; i++)
            {
                string str_sellerid = "{\"seller_id\":\"" + sc.ShoppingCartItems[i].seller_id + "\",";
                string str_mid = "\"manufacturer_id\":\"" + sc.ShoppingCartItems[i].manufacturer_id + "\",";
                string str_suitid = "\"suit_id\":\"" + sc.ShoppingCartItems[i].suit_id + "\",";
                string str_price = "\"price\":\"" + sc.ShoppingCartItems[i].price + "\",";
                string str_number = "\"count\":\"" + sc.ShoppingCartItems[i].number + "\"";
                temp = temp + str_sellerid + str_mid + str_suitid + str_price + str_number + "},";
            }
            string str_sellerid_end = "{\"seller_id\":\"" + sc.ShoppingCartItems[length-1].seller_id + "\",";
            string str_mid_end = "\"manufacturer_id\":\"" + sc.ShoppingCartItems[length - 1].manufacturer_id + "\",";
            string str_suitid_end = "\"suit_id\":\"" + sc.ShoppingCartItems[length - 1].suit_id + "\",";
            string str_price_end = "\"price\":\"" + sc.ShoppingCartItems[length - 1].price + "\",";
            string str_number_end = "\"count\":\"" + sc.ShoppingCartItems[length - 1].number + "\"";
            temp = temp + str_sellerid_end + str_mid_end + str_suitid_end + str_price_end + str_number_end + "}";
            return temp += "]}]\n";
        }
        private bool SendMessage(string message)
        {
            string fixedMessage = "{\"length\":\"" + message.Length + "\"}<EOF>\r\n" + message;
            SslTcpClient.sendMessage(fixedMessage);
            return true;
        }
        //得到服务器信息
        private string GetServerMessage()
        {
            string sLength = SslTcpClient.ReadLength();
            int length = GetLength(sLength);
            string serverMessage = SslTcpClient.ReadMessage(length);
            return serverMessage;
        }

        private int GetLength(string serverMessage)
        {
            int end = serverMessage.IndexOf("<EOF>\r\n");
            string slength = serverMessage.Substring(0, end);
            JObject jolength = JObject.Parse(slength);
            int length = int.Parse(jolength["length"].ToString());
            return length;
        }



        public int ClientGetOrderInfor(string client_id, int page, int size)
        {
            string sendMessage = ClientGetOrderSendMessage(page, size, client_id);
            SendMessage(sendMessage);
            string retMessage = GetServerMessage();

            JArray jaServerMessageQuery = JArray.Parse(retMessage);
            JObject retMessageQuery = (JObject)jaServerMessageQuery[0];
            JArray parameterMessageQuery = (JArray)jaServerMessageQuery[1];
            int length = parameterMessageQuery.Count;
            if (length == 0)
                return 0;
            JObject photoMessage = (JObject)parameterMessageQuery[length-2];
            ImageService iS = ServiceFactory.GetImageService();
            for (int j = 0; j < length - 2; j++)
            {
                JObject orderMessage = (JObject)parameterMessageQuery[j];
                ClientOrder clientOrder = new ClientOrder();
                clientOrder.orderItems = new List<Suit>();
                clientOrder.address = orderMessage["address"].ToString();
                clientOrder.receiver = orderMessage["receiver"].ToString();
                clientOrder.phonenumber = orderMessage["phonenumber"].ToString();
                clientOrder.bust = double.Parse(orderMessage["bust"].ToString());
                clientOrder.waistline = double.Parse(orderMessage["waistline"].ToString());
                clientOrder.hipline = double.Parse(orderMessage["hipline"].ToString());
                clientOrder.shoulder = double.Parse(orderMessage["shoulder"].ToString());
                clientOrder.forebreast = double.Parse(orderMessage["forebreast"].ToString());
                clientOrder.lowerlimb = double.Parse(orderMessage["lowerlimb"].ToString());
                clientOrder.upperlimb = double.Parse(orderMessage["upperlimb"].ToString());
                clientOrder.metathorax = double.Parse(orderMessage["metathorax"].ToString());
                clientOrder._id = orderMessage["_id"].ToString();
                JObject ordertime = (JObject)orderMessage["time"];
                string year = (int.Parse(ordertime["year"].ToString()) + 1900).ToString();
                string month = (int.Parse(ordertime["month"].ToString()) + 1).ToString();
                string date = (int.Parse(ordertime["date"].ToString())).ToString();
                string hour = ordertime["hours"].ToString();
                string minutes = ordertime["minutes"].ToString();
                clientOrder.time = year + "-" + month + "-" + date + " " + hour + ":" + minutes;

                JArray orderItemMessage = (JArray)orderMessage["orderItems"];
                int orderSize = orderItemMessage.Count;
                clientOrder.amount = 0;
                for (int k = 0; k < orderSize; k++)
                {

                    Suit suit = new Suit();
                    suit.count = int.Parse(orderItemMessage[k]["count"].ToString());
                    suit.manufacturer_id = orderItemMessage[k]["manufacturer_id"].ToString();
                    suit.price = double.Parse(orderItemMessage[k]["price"].ToString());
                    suit._id = orderItemMessage[k]["suit_id"].ToString();
                    suit.seller_id = orderItemMessage[k]["seller_id"].ToString();
                    JObject temp = (JObject)photoMessage[suit._id];
                    suit.photo_id = temp["photo_id"].ToString();
                    suit.suitname = temp["suitname"].ToString();
                    suit.photo = iS.GetPicture(suit.photo_id);
                    clientOrder.amount += suit.price * suit.count;
                    clientOrder.orderItems.Add(suit);
                }
                SourceManager.clientorder.Add(clientOrder);
            }
            return length-2;
        }

        //todo
        public int SellerGetOrderInfor(string seller_id,int page, int size)
        {
            string sendMessage = SellerGetOrderSendMessage(page, size, seller_id);
            SendMessage(sendMessage);
            string retMessage = GetServerMessage();

            JArray jaServerMessageQuery = JArray.Parse(retMessage);
            JObject retMessageQuery = (JObject)jaServerMessageQuery[0];
            JArray parameterMessageQuery = (JArray)jaServerMessageQuery[1];
            int length = parameterMessageQuery.Count;
            if (length == 0)
                return 0;
            JObject photoMessage = (JObject)parameterMessageQuery[length - 2];
            ImageService iS = ServiceFactory.GetImageService();
            for (int j = 0; j < length - 2; j++)
            {
                JObject orderMessage = (JObject)parameterMessageQuery[j];

                SellerOrder sellerorder = new SellerOrder();
                sellerorder.orderItems = new List<Suit>();
                sellerorder.address = orderMessage["address"].ToString();
                sellerorder.receiver = orderMessage["receiver"].ToString();
                sellerorder.phonenumber = orderMessage["phonenumber"].ToString();
                sellerorder.bust = double.Parse(orderMessage["bust"].ToString());
                sellerorder.waistline = double.Parse(orderMessage["waistline"].ToString());
                sellerorder.hipline = double.Parse(orderMessage["hipline"].ToString());
                sellerorder.shoulder = double.Parse(orderMessage["shoulder"].ToString());
                sellerorder.forebreast = double.Parse(orderMessage["forebreast"].ToString());
                sellerorder.lowerlimb = double.Parse(orderMessage["lowerlimb"].ToString());
                sellerorder.upperlimb = double.Parse(orderMessage["upperlimb"].ToString());
                sellerorder.metathorax = double.Parse(orderMessage["metathorax"].ToString());

                sellerorder._id = orderMessage["_id"].ToString();
                JObject ordertime = (JObject)orderMessage["time"];
                string year = (int.Parse(ordertime["year"].ToString()) + 1900).ToString();
                string month = (int.Parse(ordertime["month"].ToString()) + 1).ToString();
                string date = (int.Parse(ordertime["date"].ToString())).ToString();
                string hour = ordertime["hours"].ToString();
                string minutes = ordertime["minutes"].ToString();
                sellerorder.time = year + "-" + month + "-" + date + " " + hour + ":" + minutes;

                JArray orderItemMessage = (JArray)orderMessage["orderItems"];
                int orderSize = orderItemMessage.Count;
                sellerorder.amount = 0;
                for (int k = 0; k < orderSize; k++)
                {
                    SellerOrderitem sellerOrderItem = new SellerOrderitem();
                    Suit suit = new Suit();
                    suit.count = int.Parse(orderItemMessage[k]["count"].ToString());
                    suit.manufacturer_id = orderItemMessage[k]["manufacturer_id"].ToString();
                    suit.price = double.Parse(orderItemMessage[k]["price"].ToString());
                    suit._id = orderItemMessage[k]["suit_id"].ToString();
                    suit.seller_id = orderItemMessage[k]["seller_id"].ToString();
                    JObject temp = (JObject)photoMessage[suit._id];
                    suit.photo_id = temp["photo_id"].ToString();
                    suit.suitname = temp["suitname"].ToString();
                    suit.photo = iS.GetPicture(suit.photo_id);
                    sellerOrderItem.suit = suit;
                    sellerorder.amount += suit.price * suit.count;
                    sellerorder.orderItems.Add(suit);
                }
                SourceManager.sellerorder.Add(sellerorder);
            }
            return length - 2;
        }

        private string SellerGetOrderSendMessage(int page, int size,string seller_id)
        {
            return "[{\"function\":\"Query\"},{\"seller_id\":\"" + seller_id + "\",\"page\":\"" + page +   "\",\"size\":\"" + size + "\",\"type\":\"OrderBySeller\"}]\n";
        }
        private string ClientGetOrderSendMessage(int page, int size, string client_id)
        {
            return "[{\"function\":\"Query\"},{\"client_id\":\"" + client_id + "\",\"page\":\"" + page + "\",\"size\":\"" + size + "\",\"type\":\"OrderByClient\"}]\n";
        }
        public void getOrderInfor()
        {

        }


        public byte[] GetGoodsPhoto(string id)
        {
            ImageService IS = ServiceFactory.GetImageService();
            return IS.GetPicture(id);
        }
    }
}
