using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Service
{
    //图片服务
    public interface ImageService
    {
        bool SendImage(string path);
        bool SendNewImage(string path);
        string GetServerMessage();
        void SendRequest(string[] a,int size);
        double[] TakeBodyMeasurements(string frontImageName, string sideImageName, double height);
        byte[] GetPicture(string id);
    }
}
