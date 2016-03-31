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
    public class GoodsServiceImpl:GoodsService
    {
       //发送信息
        private bool SendMessage(string sendMesssageQuery)
        {
            string fixedMessage = "{\"length\":\"" + sendMesssageQuery.Length + "\"}<EOF>\r\n" + sendMesssageQuery;
            SslTcpClient.sendMessage(fixedMessage);
            return true;
        }
        //获得商品信息
        public int GetGoodsInfor(int page,int size)
        {
            string sendMessage  = "[{\"function\":\"Query\"},{\"page\":\"" + page + "\",\"size\":\"" + size + "\",\"type\":\"Suit\"}]\n";
            SendMessage(sendMessage);
            string retMessage = GetServerMessage();

            ManufacturerService mS = ServiceFactory.GetManufacturerService();
            JArray jaServerMessageQuery = JArray.Parse(retMessage);
            JObject retMessageQuery = (JObject)jaServerMessageQuery[0];
            JArray parameterMessageQuery = (JArray)jaServerMessageQuery[1];
            int i = 0;
            try
            {
                for (; i < size; i++)
                {
                    Suit s = new Suit();
                    s._id = parameterMessageQuery[i]["_id"].ToString();
                    s.seller_id = parameterMessageQuery[i]["seller_id"].ToString();
                    s.manufacturer_id = parameterMessageQuery[i]["manufacturer_id"].ToString();
                    s.suitname = parameterMessageQuery[i]["suitname"].ToString();
                    s.price = double.Parse(parameterMessageQuery[i]["price"].ToString());
                    s.photo_id = parameterMessageQuery[i]["photo_id"].ToString();
                    s.manufacturerName = mS.GetManufacturerName(s.manufacturer_id);
                    SourceManager.suits.Add(s);
                }
                return i;
            }
            catch
            {
                return i;
            }
        }
        //卖家商品信息
        public int SellerGetGoodsInfor(int page, int size,string seller_id)
        {
            string sendMessage = "[{\"function\":\"Query\"},{\"page\":\"" + page + "\",\"seller_id\":\"" + seller_id + "\",\"size\":\"" + size + "\",\"type\":\"Suit\"}]\n";
            SendMessage(sendMessage);
            string retMessage = GetServerMessage();

            ManufacturerService mS = ServiceFactory.GetManufacturerService();
            JArray jaServerMessageQuery = JArray.Parse(retMessage);
            JObject retMessageQuery = (JObject)jaServerMessageQuery[0];
            JArray parameterMessageQuery = (JArray)jaServerMessageQuery[1];
            int length = parameterMessageQuery.Count;
            int i = 0;
            try
            {
                for (; i < length; i++)
                {
                    Suit s = new Suit();
                    s._id = parameterMessageQuery[i]["_id"].ToString();
                    s.seller_id = parameterMessageQuery[i]["seller_id"].ToString();
                    s.manufacturer_id = parameterMessageQuery[i]["manufacturer_id"].ToString();

                    s.manufacturerName = mS.GetManufacturerName(s.manufacturer_id);
                    s.suitname = parameterMessageQuery[i]["suitname"].ToString();
                    s.price = double.Parse(parameterMessageQuery[i]["price"].ToString());
                    s.photo_id = parameterMessageQuery[i]["photo_id"].ToString();
                    SourceManager.SellerSuits.Add(s);
                }
                return i;
            }
            catch
            {
                return i;
            }
        }

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

        public byte[] GetGoodsPhoto(string id)
        {
            ImageService IS = ServiceFactory.GetImageService();
            return IS.GetPicture(id);
        }
        //上传图片
        public bool UpdatePhoto(string photo_id, string path)
        {
            string sendmassage = "[{\"function\":\"Modify\"},{\"type\":\"Photo\",\"_id\":\"" + photo_id + "\"}]\n";
            SendMessage(sendmassage);
            ImageService iS = ServiceFactory.GetImageService();
            iS.SendImage(path);
            string serverMessage = GetServerMessage();
            JArray jaServerMessage = JArray.Parse(serverMessage);
            JObject joretMessage = (JObject)jaServerMessage[0];
            if (joretMessage.GetValue("ret").ToString() == "success")
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        //修改信息
        public string ModifyInfor(string suitname, double price,string suit_id)
        {
            string sendmessage = "[{\"function\":\"Modify\"},{\"type\":\"Suit\",\"_id\":\"" + suit_id + "\",\"suitname\":\"" + suitname + "\",\"price\":\"" +price.ToString() + "\"}]\n";
            SendMessage(sendmessage);
            return GetServerMessage();
        }
        public bool IsSuccess(string retMessage)
        {
            JArray jaServerMessage = JArray.Parse(retMessage);
            JObject joretMessage = (JObject)jaServerMessage[0];
            if (joretMessage.GetValue("ret").ToString() == "success")
            {
                return true;
            }
            string errorMessage = GetFailedMessage(retMessage);
            SourceManager.ErrorMessage = errorMessage;
            return false;
        }
        //删除商品
        public bool DeleteGoods(string suit_id)
        {
            string sendmessage = "[{\"function\":\"Delete\"},{\"type\":\"Suit\",\"_id\":\"" + suit_id +  "\"}]\n";
            SendMessage(sendmessage);
            string serverMessage = GetServerMessage();
            JArray jaServerMessage = JArray.Parse(serverMessage);
            JObject joretMessage = (JObject)jaServerMessage[0];
            if (joretMessage.GetValue("ret").ToString() == "success")
            {
                return true;
            }
            else
            {
                SourceManager.ErrorMessage = GetFailedMessage(serverMessage);
                return false;
            }
        }
        //添加商品
        public bool CreateGoods(string path, string suitname, double price,string seller_id,string manufacturer_id)
        {
            ImageService iS = ServiceFactory.GetImageService();
            iS.SendNewImage(path);
            string serverMessage = GetServerMessage();
            JArray jaServerMessage = JArray.Parse(serverMessage);
            JObject joretMessage = (JObject)jaServerMessage[0];
            if (joretMessage.GetValue("ret").ToString() == "success")
            {
                JObject jophotoMessage = (JObject)jaServerMessage[1];
                string photo_id = jophotoMessage["_id"].ToString();
                string sendmessage = "[{\"function\":\"Create\"},{\"type\":\"Suit\",\"seller_id\":\"" + seller_id + "\",\"manufacturer_id\":\"" + manufacturer_id + "\",\"suitname\":\"" + suitname + "\",\"price\":\"" + price.ToString() + "\",\"photo_id\":\"" + photo_id + "\"}]\n";
                SendMessage(sendmessage);
                string serverMessage1 = GetServerMessage();
                JArray jaServerMessage1 = JArray.Parse(serverMessage1);
                JObject joretMessage1 = (JObject)jaServerMessage1[0];
                if (joretMessage1.GetValue("ret").ToString() == "success")
                {
                    return true;
                }
                else
                {
                    string failMessage = GetFailedMessage(serverMessage);
                    SourceManager.ErrorMessage = failMessage;
                    return false;
                }
            }
            else
            {
                string failMessage = GetFailedMessage(serverMessage);
                SourceManager.ErrorMessage = failMessage;
                return false;
            }
         
        }
        //查询商品
        public int UserGetGoodsInforByName(string keyword, int page, int size)
        {
            string sendMessage = "[{\"function\":\"Query\"},{\"page\":\"" + page + "\",\"size\":\"" + size + "\",\"suitname\":\"" + keyword + "\",\"type\":\"SuitBySuitname\"}]\n";
            SendMessage(sendMessage);
            string retMessage = GetServerMessage();

            ;
            JArray jaServerMessageQuery = JArray.Parse(retMessage);
            JObject retMessageQuery = (JObject)jaServerMessageQuery[0];
            JArray parameterMessageQuery = (JArray)jaServerMessageQuery[1];
            int i = 0;
            try
            {
                for (; i < size; i++)
                {
                    Suit s = new Suit();
                    s._id = parameterMessageQuery[i]["_id"].ToString();
                    s.seller_id = parameterMessageQuery[i]["seller_id"].ToString();
                    s.manufacturer_id = parameterMessageQuery[i]["manufacturer_id"].ToString();
                    s.suitname = parameterMessageQuery[i]["suitname"].ToString();
                    s.price = double.Parse(parameterMessageQuery[i]["price"].ToString());
                    s.photo_id = parameterMessageQuery[i]["photo_id"].ToString();
                    string t = parameterMessageQuery[i]["information"].ToString();
                    int j = 0;
                    int ilength = 0;
                    while (t.IndexOf(",", j) >= 0)
                    {
                        j = t.IndexOf(",", j) + 1;
                        ilength++;
                    }
                    ilength++;
                    s.information = new string[ilength];
                    for (int k = 0; k < ilength; k++)
                    {
                        s.information[k] = ((JArray)parameterMessageQuery[i]["information"])[k].ToString();
                    }
                    SourceManager.UserSearchSuits.Add(s);
                }
                return i;
            }
            catch
            {
                return i;
            }

        }

        //获得错误信息
        public string GetFailedMessage(string ReturnMessage)
        {
            JArray jaServerMessage = JArray.Parse(ReturnMessage);
            JObject joretMessage = (JObject)jaServerMessage[1];
            return joretMessage.GetValue("message").ToString();
        }
    }
}
