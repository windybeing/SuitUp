using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Chpoi.SuitUp.Entity;

namespace Chpoi.SuitUp.Source
{
    //用于将关键信息保存到内存中
    public class SourceManager
    {
        //保存对象内容
        public static Client client;
        public static ShoppingCart shoppingcart;
        public static Seller seller;
        public static Order order;
        //列出物品
        public static List<Suit> suits;
        public static List<Suit> SellerSuits;
        public static bool end;
        public static int pagemax;
        public static int clientLastPageCount;
        public static bool sellerSuitEnd;
        public static int sellerSuitPageMax;
        public static int sellerLastPageCount;

        //列出订单
        public static List<SellerOrder> sellerorder;
        public static List<ClientOrder> clientorder;
        public static bool sellerOrderEnd;
        public static bool clientOrderEnd;
        public static int clientOrderPageMax;
        public static int sellerOrderPageMax;
        public static int clientOrderLastPageCount;
        public static int sellerOrderLastPageCount;

        //详情订单
        public static ClientOrder curOrder;
        public static SellerOrder sellerCurOrder;


        //详情服装
        public static Suit SellerCurSuit;
        public static string sellerNewPicPath;

        //查询信息
        public static bool UserIsSearch;
        public static string UserKeyword;
        public static List<Suit> UserSearchSuits;
        public static bool UserSearchEnd;
        public static int UserSearchPageMax;
        public static int clientSearchLastPageCount;

        //错误信息
        public static string ErrorMessage;

    }
}
