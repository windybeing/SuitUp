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
    static public class ServiceFactory
    {
        private static ClientService clientSer{get;set;}
        private static GoodsService goodsSer { get; set; }
        private static ImageService imageSer { get; set; }
        private static ShoppingCartService shoppingcartSer { get; set; }
        private static SslService sslSer { get; set; }
        private static SellerService sellerSer { get; set; }
        private static OrderService orderSer { get; set; }
        private static ScanService scanSer { get; set; }
        private static ManufacturerService manSer { get; set; }
        static ServiceFactory()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("configuration/ServiceFactoryConfig.xml");
            XmlNode xmlNode0 = xmlDoc.ChildNodes[1].ChildNodes[0];
            XmlNode xmlNode1 = xmlDoc.ChildNodes[1].ChildNodes[1];
            XmlNode xmlNode2 = xmlDoc.ChildNodes[1].ChildNodes[2];
            XmlNode xmlNode3 = xmlDoc.ChildNodes[1].ChildNodes[3];
            XmlNode xmlNode4 = xmlDoc.ChildNodes[1].ChildNodes[4];
            XmlNode xmlNode5 = xmlDoc.ChildNodes[1].ChildNodes[5];
            XmlNode xmlNode6 = xmlDoc.ChildNodes[1].ChildNodes[6];
            XmlNode xmlNode7 = xmlDoc.ChildNodes[1].ChildNodes[7];
            XmlNode xmlNode8 = xmlDoc.ChildNodes[1].ChildNodes[8];
            //load后加程序集名称
            clientSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode0.ChildNodes[0].Value) as ClientService;
            goodsSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode1.ChildNodes[0].Value) as GoodsService;
            imageSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode2.ChildNodes[0].Value) as ImageService;
            shoppingcartSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode3.ChildNodes[0].Value) as ShoppingCartService;
            sslSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode4.ChildNodes[0].Value) as SslService;
            sellerSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode5.ChildNodes[0].Value) as SellerService;
            orderSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode6.ChildNodes[0].Value) as OrderService;
            scanSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode7.ChildNodes[0].Value) as ScanService;
            manSer = Assembly.Load("SuitUpClient").CreateInstance("Chpoi.SuitUp.ServiceImpl." + xmlNode8.ChildNodes[0].Value) as ManufacturerService;
        }

        public static ClientService GetClientService()
        {
            return clientSer;
        }
        public static GoodsService GetGoodsService()
        {
            return goodsSer;
        }

        public static ImageService GetImageService()
        {
            return imageSer;
        }
        public static ShoppingCartService GetShoppingCartService()
        {
            return shoppingcartSer;
        }

        public static SslService GetSslService()
        {
            return sslSer;
        }
        public static SellerService GetSellerService()
        {
            return sellerSer;
        }
        public static OrderService GetOrderService()
        {
            return orderSer;
        }
        public static ScanService GetScanService()
        {
            return scanSer;
        }
        public static ManufacturerService GetManufacturerService()
        {
            return manSer;
        }
    }
}
