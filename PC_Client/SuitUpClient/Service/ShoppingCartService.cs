using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Service
{
    //购物车服务
    public interface ShoppingCartService
    {
        string GetSendMessage(int page, int size);
        bool SendMessage(string message);
        void GetShoppingCartItemInfor(string ReturnMessage);
        string GetServerMessage();
        bool payorder();
    }
}
