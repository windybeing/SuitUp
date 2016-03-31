using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Entity
{
    public class ShoppingCartItem
    {
        public string seller_id { get; set; }
        public string manufacturer_id { get; set; }
        public string suit_id { get; set; }
        public string suitname { get; set; }
        public double price { get; set; }
        public int number { get; set; }
        public ShoppingCartItem(string seller_id,string manufacturer_id, string suit_id,string suitname, double price, int number)
        {
            this.seller_id = seller_id;
            this.manufacturer_id = manufacturer_id;
            this.suit_id = suit_id;
            this.suitname = suitname;
            this.price = price;
            this.number = number;
        }
    }
}
