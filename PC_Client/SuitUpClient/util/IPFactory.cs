using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Reflection;
using System.Xml;
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.ServiceImpl;

namespace Chpoi.SuitUp.Util
{
    static public class IPFactory
    {
        private static string ip {get;set;}
        static IPFactory()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("configuration/IPFactoryConfig.xml");
            XmlNode xmlNode0 = xmlDoc.ChildNodes[1].ChildNodes[0];
            //load后加程序集名称
            XmlElement xe = (XmlElement)xmlNode0;
            ip = xe.LastChild.Value;
            //ip = Assembly.Load("SuitUpClient.").CreateInstance(xmlNode0.ChildNodes[0].Value) as string;
        }

        public static string GetIP()
        {
            return ip;
        }
    }
}
