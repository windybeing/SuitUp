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
using System;
using System.Runtime.InteropServices;
using System.IO;

namespace Chpoi.SuitUp.ServiceImpl
{
    public class ScanServiceImpl:ScanService
    {
        //3d扫描
        [DllImport("ImageService", EntryPoint = "ImageService", ExactSpelling = false, CallingConvention = CallingConvention.Cdecl)]
        extern static IntPtr ImageService(byte[] fileName);
        public void scan()
        {
            if (File.Exists("ObjFile/3dscan.obj"))
            {
                File.Delete("ObjFile/3dscan.obj");
            }
            //生成3D扫描文件
            System.Diagnostics.Process.Start("DF_3DScan_d.exe").WaitForExit();
            IntPtr result;
            //获得人体参数
            result = ImageService(System.Text.Encoding.Default.GetBytes("ObjFile/3dscan.obj"));
            byte[] t = new byte[1000];
            Marshal.Copy(result, t, 0, 1000);
            string tt = System.Text.Encoding.Default.GetString(t);
            string temp = tt.Replace("\0", "");
            string[] bodyparameter = temp.Split(new char[]{' '});

            SourceManager.client.bust = Convert.ToDouble((double.Parse(bodyparameter[0]) * 100).ToString("0.00"));
            SourceManager.client.waistline = Convert.ToDouble((double.Parse(bodyparameter[1]) * 100).ToString("0.00"));
            SourceManager.client.hipline = Convert.ToDouble((double.Parse(bodyparameter[2]) * 100).ToString("0.00"));
            SourceManager.client.shoulder = Convert.ToDouble((double.Parse(bodyparameter[3]) * 100).ToString("0.00"));
            SourceManager.client.forebreast = Convert.ToDouble((double.Parse(bodyparameter[4]) * 100).ToString("0.00"));
            SourceManager.client.metathorax = Convert.ToDouble((double.Parse(bodyparameter[5]) * 100).ToString("0.00"));
            SourceManager.client.lowerlimb = Convert.ToDouble((double.Parse(bodyparameter[6]) * 100).ToString("0.00"));
            SourceManager.client.upperlimb = Convert.ToDouble((double.Parse(bodyparameter[7]) * 100).ToString("0.00"));

        }
    }
}
