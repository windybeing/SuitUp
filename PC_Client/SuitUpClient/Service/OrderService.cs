using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Chpoi.SuitUp.Entity;

namespace Chpoi.SuitUp.Service
{
    //订单服务
    public interface OrderService
    {
        bool PlaceOrder(ShoppingCart sc, string client_id, string address, string recipient, string postCode, string phoneNumberdouble ,double bust,double waistline,double hipline,double shoulder,double forebreast,double metathorax,double upperlimb,double lowerlimb);
        void getOrderInfor();
        int SellerGetOrderInfor(string seller_id, int page, int size);
        int ClientGetOrderInfor(string client_id, int page, int size);
        byte[] GetGoodsPhoto(string id);
    }
}
