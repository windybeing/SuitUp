using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Chpoi.SuitUp.Source;
using Chpoi.SuitUp.SSL;
using Chpoi.SuitUp.Service;

namespace Chpoi.SuitUp.ServiceImpl
{
    public class ShoppingCartServiceImpl : ShoppingCartService
    {
        public string GetSendMessage(int page, int size)
        {
            return "";
        }
        public bool SendMessage(string message) 
        {
            return true;
        }
        public void GetShoppingCartItemInfor(string ReturnMessage)
        {
        }
        public string GetServerMessage()
        {
            return "";
        }

        public bool payorder()
        {
            return true;
        }

    }
}
