using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Chpoi.SuitUp.Service
{
    public interface ImageService
    {
        double[] TakeBodyMeasurements(string frontImageName, string sideImageName, double height);
    }
}
