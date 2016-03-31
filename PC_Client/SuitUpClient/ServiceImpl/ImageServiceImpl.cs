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
    class ImageServiceImpl:ImageService
    {
        //获得图片
        public byte[] GetPicture(string id)
        {
            string[] ids = { id };
            SendRequest(ids, 1);
            GetServerMessage();
            int length = GetLength(SslTcpClient.ReadLength());
                string image = SslTcpClient.ReadPicture(length);
                byte[] byteArray = System.Text.Encoding.GetEncoding("ISO-8859-1").GetBytes(image);

                return byteArray;
        }

        //发送新图片
        public bool SendNewImage(string path)
        {
            FileInfo fileinfo = new FileInfo(path);
            byte[] buf = new byte[fileinfo.Length];
            FileStream fs = new FileStream(path, FileMode.Open, FileAccess.Read);
            fs.Read(buf, 0, buf.Length);
            fs.Close();
            GC.ReRegisterForFinalize(fileinfo);
            GC.ReRegisterForFinalize(fs);
            string image = System.Text.Encoding.GetEncoding("iso-8859-1").GetString(buf);
            string functionMessage = "[{\"function\":\"Create\"},{\"type\":\"Photo\"}]\r\n";
            string functionMessageLength = "{\"length\":\"" + functionMessage.Length + "\"}<EOF>\r\n";
            string imagelenth = "{\"length\":\"" + image.Length + "\"}<EOF>\r\n";
            string totalMessage = functionMessageLength + functionMessage + imagelenth + image;
            SslTcpClient.sendMessage(totalMessage);
            return true;
        }



        public bool SendImage(string path)
        {
            FileInfo fileinfo = new FileInfo(path);
            byte[] buf = new byte[fileinfo.Length];
            FileStream fs = new FileStream(path, FileMode.Open, FileAccess.Read);
            fs.Read(buf, 0, buf.Length);
            fs.Close();
            GC.ReRegisterForFinalize(fileinfo);
            GC.ReRegisterForFinalize(fs);
            string image = System.Text.Encoding.GetEncoding("iso-8859-1").GetString(buf);

            string imagelenth = "{\"length\":\"" + image.Length + "\"}<EOF>\r\n";
            string totalMessage = imagelenth + image;
            SslTcpClient.sendMessage(totalMessage);
            return true;
        }
        //得到服务器信息
        public string GetServerMessage()
        {
            string sLength = SslTcpClient.ReadLength();
            int length = GetLength(sLength);
            string serverMessage;
            serverMessage = SslTcpClient.ReadMessage(length);
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

        public void SendRequest(string[] a,int size)
        {
            string functionMessage = "[{\"function\":\"Get\"},{\"photos_id\":[";
            //type\":\"Suit\"}]\r\n";
            for(int i = 0; i< size-1;i++){
                functionMessage += "\"";
                functionMessage += a[i];
                functionMessage += "\"";
                functionMessage += ",";
            }
            functionMessage += "\"";
            functionMessage += a[size-1].ToString();
            functionMessage += "\"";
            functionMessage += "],\"type\":\"Photo\"}]\n";
            string functionMessageLength = "{\"length\":\"" + functionMessage.Length + "\"}<EOF>\r\n";
            string totalMessage = functionMessageLength + functionMessage;
            SslTcpClient.sendMessage(totalMessage);

        }


        public double[] TakeBodyMeasurements(string frontImageName,
            string sideImageName, double height)
        {
            Image<Gray, byte> frontContourImage = PreTreatment(frontImageName);
            //frontContourImage.Save("D:/sky-fly/temp/t2.jpg");
            int[] frontBody = GetBody(frontContourImage);
            //获取头顶
            int TopHeight = frontBody[frontContourImage.Height];

            //获取脚底
            int bottomHeight = frontBody[frontContourImage.Height + 1];
            //minHeight = maxHeight - (maxHeight - minHeight) / 20;

            double frontHeight = bottomHeight - TopHeight;
            double buttocksLine = bottomHeight - (frontHeight) * 0.5;
            double waistLine = bottomHeight - (frontHeight) * 0.6;
            double chestLine = bottomHeight - (frontHeight) * 0.7;

            int buttocks1 = GetWidth(Convert.ToInt32(buttocksLine), frontBody[Convert.ToInt32(buttocksLine)], frontContourImage);
            int waist1 = GetWidth(Convert.ToInt32(waistLine), frontBody[Convert.ToInt32(waistLine)], frontContourImage);
            int chest1 = GetWidth(Convert.ToInt32(chestLine), frontBody[Convert.ToInt32(chestLine)], frontContourImage);


            Image<Gray, byte> sideContourImage = PreTreatment(sideImageName);
            //sideContourImage.Save("D:/sky-fly/temp/t3.jpg");
            int[] sideBody = GetBody(sideContourImage);
            //获取头顶
            TopHeight = sideBody[sideContourImage.Height];

            //获取脚底
            bottomHeight = sideBody[sideContourImage.Height + 1];

            double sideHeight = bottomHeight - TopHeight;
            buttocksLine = bottomHeight - (sideHeight) * 0.5;
            waistLine = bottomHeight - (sideHeight) * 0.6;
            chestLine = bottomHeight - (sideHeight) * 0.7;

            int buttocks2 = GetWidth(Convert.ToInt32(buttocksLine), sideBody[Convert.ToInt32(buttocksLine)], sideContourImage);
            int waist2 = GetWidth(Convert.ToInt32(waistLine), sideBody[Convert.ToInt32(waistLine)], sideContourImage);
            int chest2 = GetWidth(Convert.ToInt32(chestLine), sideBody[Convert.ToInt32(buttocksLine)], sideContourImage);


            double[] measuerments = new double[3];
            double chestWidth1 = height * chest1 / frontHeight;
            double chestWidth2 = height * chest2 / sideHeight;
            double waistWidth1 = height * waist1 / frontHeight;
            double waistWidth2 = height * waist2 / sideHeight;
            double buttocksWidth1 = height * buttocks1 / frontHeight;
            double buttocksWidth2 = height * buttocks2 / sideHeight;

            //胸围
            measuerments[0] = 128.83 - 2.112 * chestWidth1 - 4.232 * chestWidth2 - 0.054 *
                chestWidth1 * chestWidth1 - 0.063 * chestWidth2 * chestWidth2 + 0.291 * chestWidth1 * chestWidth2;
            //腰围
            measuerments[1] = 101.99 - 4.801 * waistWidth1 + 0.294 * waistWidth2 + 0.014 *
                waistWidth1 * waistWidth1 - 0.152 * waistWidth2 * waistWidth2 + 0.277 * waistWidth2 * waistWidth1;
            //臀围
            measuerments[2] = 217.07 - 9.616 * buttocksWidth1 - 0.366 * buttocksWidth2 +
                0.174 * buttocksWidth1 * buttocksWidth1 + 0.052 * buttocksWidth2 * buttocksWidth2 - 0.026 * buttocksWidth1 * buttocksWidth2;
            //Console.WriteLine(measuerments[0]);
            //Console.WriteLine(measuerments[1]);
            //Console.WriteLine(measuerments[2]);
            /*

            string WaistNetData = "D:/sky-fly/temp/DATA/cascade.xml";
            CascadeClassifier waist = new CascadeClassifier(WaistNetData);
            Rectangle[] waistDetect = waist.DetectMultiScale(grayEqualizeImage, 1.1, 10, new Size(20, 20), Size.Empty);

            Console.WriteLine(waistDetect[0].Left);
            Console.WriteLine(waistDetect[0].Right);
            Console.WriteLine(waistDetect[0].Top);
            Console.WriteLine(waistDetect[0].Bottom);

            */
            return measuerments;

        }

        private int[] GetBody(Image<Gray, byte> contourImage)
        {
            //额外加2是为了方便传顶部和底部的高度。
            int[] Body = new int[contourImage.Height + 2];
            for (int i = 0; i != contourImage.Height + 2; i++)
            {
                Body[i] = -1;
            }
            int x = contourImage.Width / 2;
            int y = contourImage.Height / 2;
            for (; ; y--)
            {
                Body[y] = x;
                if (contourImage.Data[y, x, 0] == 255)
                {
                    y++;
                    int left, right;
                    for (left = x; contourImage.Data[y, left, 0] != 255; left--) { }
                    for (right = x; contourImage.Data[y, right, 0] != 255; right++) { }
                    x = (left + right) / 2;
                    if (contourImage.Data[y - 1, x, 0] == 255)
                    {
                        break;
                    }
                }
                else
                    contourImage.Data[y, x, 0] = 254;
            }
            Body[contourImage.Height] = y;
            y = contourImage.Height / 2;
            x = contourImage.Width / 2;
            for (; ; y++)
            {
                Body[y] = x;
                if (contourImage.Data[y, x, 0] == 255)
                {
                    y--;
                    int left, right;
                    for (left = x; contourImage.Data[y, left, 0] != 255; left--) { }
                    for (right = x; contourImage.Data[y, right, 0] != 255; right++) { }
                    x = (left + right) / 2;
                    if (contourImage.Data[y + 1, x, 0] == 255)
                    {
                        break;
                    }
                }
                else
                    contourImage.Data[y, x, 0] = 254;
            }
            Body[contourImage.Height + 1] = y;
            return Body;
        }

        private Image<Gray, byte> PreTreatment(string imageName)
        {

            //获取原始图
            Image<Bgr, byte> srcImage = new Image<Bgr, byte>(imageName);

            //获取缩放图
            Image<Bgr, byte> resizedImage = new Image<Bgr, byte>(CvInvoke.cvGetSize(srcImage));
            CvInvoke.cvResize(srcImage, resizedImage, Emgu.CV.CvEnum.INTER.CV_INTER_AREA);

            //获取灰化图 
            Image<Gray, byte> grayImage = new Image<Gray, byte>(CvInvoke.cvGetSize(resizedImage));
            CvInvoke.cvCvtColor(resizedImage, grayImage, Emgu.CV.CvEnum.COLOR_CONVERSION.RGB2GRAY);

            //直方图均衡化
            Image<Gray, byte> grayEqualizeImage = new Image<Gray, byte>(CvInvoke.cvGetSize(grayImage));
            CvInvoke.cvEqualizeHist(grayImage, grayEqualizeImage);


            //获取二值图
            Image<Gray, byte> binImage = new Image<Gray, byte>(CvInvoke.cvGetSize(grayEqualizeImage));
            CvInvoke.cvThreshold(grayImage, binImage, 149, 255, Emgu.CV.CvEnum.THRESH.CV_THRESH_BINARY);


            //获取轮廓图
            Image<Gray, byte> contourImage = new Image<Gray, byte>(CvInvoke.cvGetSize(binImage));
            Contour<Point> contour =
                binImage.FindContours(Emgu.CV.CvEnum.CHAIN_APPROX_METHOD.CV_CHAIN_APPROX_SIMPLE, Emgu.CV.CvEnum.RETR_TYPE.CV_RETR_LIST);

            //排除过小轮廓
            double minArea = contourImage.Height * contourImage.Width / 25;
            for (; ; contour = contour.HNext)
            {
                double tempArea = CvInvoke.cvContourArea(contour, new MCvSlice(0, 10000), 0);
                if (tempArea < minArea)
                {
                    if (contour.HPrev != null)
                    {
                        contour.HPrev.HNext = contour.HNext;
                        contour.HNext.HPrev = contour.HPrev;
                    }
                    else
                        contour.HNext.HPrev = null;
                }
                if (contour.HNext == null)
                    break;

            }
            int to = 0;
            for (; ; contour = contour.HPrev)
            {
                to++;
                if (contour.HPrev == null)
                    break;
            }

            CvInvoke.cvDrawContours(contourImage, contour, new MCvScalar(255),
                new MCvScalar(255), 1, 1, Emgu.CV.CvEnum.LINE_TYPE.EIGHT_CONNECTED, new Point(0, 0));
            return contourImage;
        }
        private int GetWidth(int h, int x, Image<Gray, byte> contourImage)
        {
            //获取该位置长度
            int left = x;
            int right = left;
            for (; contourImage.Data[h, left, 0] != 255; left--) { contourImage.Data[h, left, 0] = 254; }
            right++;
            for (; contourImage.Data[h, right, 0] != 255; right++) { contourImage.Data[h, right, 0] = 254; }
            return right - left;
        }
    }
}
