using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Chpoi.SuitUp.Service;
using Chpoi.SuitUp.SSL;
using Chpoi.SuitUp.Util;

namespace Chpoi.SuitUp.ServiceImpl
{
    class SslServiceImpl :SslService
    {
        public bool RunClient()
        {
            string ip = IPFactory.GetIP();
            return SslTcpClient.RunClient(ip, "192.168.1.68");
        }
    }
}
