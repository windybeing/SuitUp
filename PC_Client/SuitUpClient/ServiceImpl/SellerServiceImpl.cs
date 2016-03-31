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
namespace Chpoi.SuitUp.ServiceImpl
{
    public class SellerServiceImpl:SellerService
    {
        //登录
        public string Login(string username, string password)
        {
            string sendMessage = LoginGetSendMessage(username, password);
            SendMessage(sendMessage);
            return GetServerMessage();
        }
        //得到错误信息
        public string GetFailedMessage(string ReturnMessage)
        {
            JArray jaServerMessage = JArray.Parse(ReturnMessage);
            JObject joretMessage = (JObject)jaServerMessage[1];
            return joretMessage.GetValue("message").ToString();
        }

        private string LoginGetSendMessage(string username, string password)
        {
            return "[{\"function\":\"Login\"},{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"type\":\"Seller\"}]\n";
        }
        private bool SendMessage(string message)
        {
            string fixedMessage = "{\"length\":\"" + message.Length + "\"}<EOF>\r\n" + message;
            SslTcpClient.sendMessage(fixedMessage);
            return true;
        }
        private string GetServerMessage()
        {
            string sLength = SslTcpClient.ReadLength();
            int length = GetLength(sLength);
            string serverMessage = SslTcpClient.ReadMessage(length);
            return serverMessage;
        }

        //修改个人信息
        public string ModifyInfor(string ClientName, int Age, string Address, string Password)
        {
            string sendMessage = ModifyInforGetSendMessage(ClientName,Age,Address,Password);
            SendMessage(sendMessage);
            return GetServerMessage();
        }

        private string ModifyInforGetSendMessage(string ClientName, int Age, string Address,string password)
        {
            string sendmassage = "[{\"function\":\"Modify\"},{\"type\":\"Seller\",\"_id\":\"" + SourceManager.seller._id + "\"";
            if (ClientName != SourceManager.client.clientname)
            {
                sendmassage += ",\"clientname\":\"" + ClientName + "\"";
            }
            if (Age != SourceManager.client.age)
            {
                sendmassage += ",\"age\":\"" + Age.ToString() + "\"";
            }
            if (Address != SourceManager.client.address)
            {
                sendmassage += ",\"address\":\"" + Address + "\"";
            }
            if (password != SourceManager.client.password)
            {
                sendmassage += ",\"password\":\"" + password + "\"";
            }
            sendmassage += "}]\n";
            return sendmassage;
        }

        private int GetLength(string serverMessage)
        {
            int end = serverMessage.IndexOf("<EOF>\r\n");
            string slength = serverMessage.Substring(0, end);
            JObject jolength = JObject.Parse(slength);
            int length = int.Parse(jolength["length"].ToString());
            return length;
        }
        //得到卖家信息
        public void GetSellerInfor(string retMessage)
        {
            JArray jaServerMessage = JArray.Parse(retMessage);
            JObject parameterMessage = (JObject)jaServerMessage[1];

            Seller s = new Seller();
            s.username = parameterMessage["username"].ToString();
            s.password = parameterMessage["password"].ToString();
            s.email = parameterMessage["email"].ToString();
            s.phonenumber = parameterMessage["phonenumber"].ToString();
            s._id = parameterMessage["_id"].ToString();
            s.manufacturer_id = parameterMessage["manufacturer_id"].ToString();
            SourceManager.seller = s;
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


        //注册
        public string Register(string Username, string Password, string Email, string PhoneNumber, string identification)
        {
            string sendMessage = RegisterGetSendMessage(Username, Password,Email,PhoneNumber,identification);
            SendMessage(sendMessage);
            return GetServerMessage();
        }
        private string RegisterGetSendMessage(string username, string password, string Email, string PhoneNumber, string identification)
        {
            return "[{\"function\":\"Register\"},{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"email\":\"" + Email + "\",\"phonenumber\":\"" + PhoneNumber + "\",\"identification\":\"" + identification + "\",\"type\":\"Seller\"}]\n";
        }
    }
}
