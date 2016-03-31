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
    public class ClientServiceImpl:ClientService
    {
        //登录
        public string Login(string username, string password)
        {
            string sendMessage = LoginGetSendMessage(username, password);
            SendMessage(sendMessage);
            return GetServerMessage();

        }
        //获得错误信息
        public string GetFailedMessage(string ReturnMessage)
        {
            JArray jaServerMessage = JArray.Parse(ReturnMessage);
            JObject joretMessage = (JObject)jaServerMessage[1];
            return joretMessage.GetValue("message").ToString();
        }
        //登录信息
        private string LoginGetSendMessage(string username, string password)
        {
            return "[{\"function\":\"Login\"},{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"type\":\"Client\"}]\n";
        }
        private bool SendMessage(string message)
        {
            string fixedMessage = "{\"length\":\"" + message.Length + "\"}<EOF>\r\n" + message;
            SslTcpClient.sendMessage(fixedMessage);
            return true;
        }
        //获得服务器信息
        private string GetServerMessage()
        {
                string sLength = SslTcpClient.ReadLength();
                int length = GetLength(sLength);
                string serverMessage = SslTcpClient.ReadMessage(length);
                return serverMessage;
        }


        public string ModifyInfor(string ClientName, int Age, string Address, string Password)
        {
            string sendMessage = ModifyInforGetSendMessage(ClientName,Age,Address,Password);
            SendMessage(sendMessage);
            return GetServerMessage();
        }

        //修改个人信息
        private string ModifyInforGetSendMessage(string ClientName, int Age, string Address,string password)
        {
            string sendmassage = "[{\"function\":\"Modify\"},{\"type\":\"Client\",\"_id\":\"" + SourceManager.client._id + "\"";
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
        //得到用户个人信息
        public void GetUserInfor(string retMessage)
        {
            JArray jaServerMessage = JArray.Parse(retMessage);
            JObject parameterMessage = (JObject)jaServerMessage[1];

            Client c = new Client();
            c.username = parameterMessage["username"].ToString();
            c.password = parameterMessage["password"].ToString();
            c.email = parameterMessage["email"].ToString();
            c.age = int.Parse(parameterMessage["age"].ToString());
            c.address = parameterMessage["address"].ToString();
            c.phonenumber = parameterMessage["phonenumber"].ToString();
            c.clientname = parameterMessage["clientname"].ToString();
            c._id = parameterMessage["_id"].ToString();
            SourceManager.client = c;
        }
        //用户注册
        public string Register(string Username, string Password,string Email,string PhoneNumber)
        {
            string sendMessage = RegisterGetSendMessage(Username, Password,Email,PhoneNumber);
            SendMessage(sendMessage);
            return GetServerMessage();
        }
        private string RegisterGetSendMessage(string username, string password, string Email,string PhoneNumber)
        {
            return "[{\"function\":\"Register\"},{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"email\":\"" + Email + "\",\"phonenumber\":\"" + PhoneNumber +"\",\"type\":\"Client\"}]\n";
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
        //上传头像

        public bool UpdateAvatar(string photo_id, string path)
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
    }
}
