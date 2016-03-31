using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Chpoi.SuitUp.Entity;

namespace Chpoi.SuitUp.Service
{
    //商品服务
    public interface GoodsService
    {
        int GetGoodsInfor(int page, int size);
        byte[] GetGoodsPhoto(string id);
        bool UpdatePhoto(string photo_id,string path);
        string ModifyInfor(string suitname, double price, string suit_id);
        bool DeleteGoods(string suit_id);
        bool CreateGoods(string path, string suitname, double price, string seller_id, string manufacturer_id);
        int SellerGetGoodsInfor(int page, int size,string seller_id);
        int UserGetGoodsInforByName(string keyword,int page,int size);
        bool IsSuccess(string retMessage);
    }
}
