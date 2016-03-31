using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Emgu.CV;
using Emgu.CV.Structure;
using Emgu.CV.UI;
using Emgu.Util;
using System.Drawing;
using Chpoi.SuitUp.SSL;
using Chpoi.SuitUp.Source;
using Chpoi.SuitUp.Service;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace Chpoi.SuitUp.ServiceImpl
{
    class ManufacturerServiceImpl :ManufacturerService
    {
        //获得厂商名称
        public string GetManufacturerName(string manufacturer_id)
        {
            string sendMessage = "[{\"function\":\"Get\"},{\"manufacturers_id\":[\"" + manufacturer_id + "\"],\"type\":\"Manufacturer\"}]\n";
            SendMessage(sendMessage);
            string retMessage = GetServerMessage();

            JArray jaServerMessageQuery = JArray.Parse(retMessage);
            JObject retMessageQuery = (JObject)jaServerMessageQuery[0];
            //todo
            if (retMessageQuery.GetValue("ret").ToString() == "success")
            {
                JArray parameterMessageQuery = (JArray)jaServerMessageQuery[1];
                JObject joMessage = (JObject)parameterMessageQuery[0];
                return joMessage["manufacturername"].ToString();
            }
            else
            {
                return "-1";
            }
           
        }
        //发送信息
        private bool SendMessage(string sendMesssageQuery)
        {
            string fixedMessage = "{\"length\":\"" + sendMesssageQuery.Length + "\"}<EOF>\r\n" + sendMesssageQuery;
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
    }
}
