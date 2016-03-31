using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Entity
{
    public class Suit
    {
        public string manufacturer_id { get; set; }
        public string _id { get; set; }
        public string seller_id { get; set; }
        public string suitname { get; set; }
        public double price { get; set; }
        public string[] information{get;set;}
        public string photo_id { get; set; }
        public byte[] photo { get; set; }
        public int count { get; set; }
        public string manufacturerName { get; set; }

        public Suit() { }
        public Suit(string suitname, double Price)
        {
            this.suitname = suitname;
            this.price = price;
        }
        public Suit(string suitname, double price,string[] information)
        {
            this.suitname = suitname;
            this.price = price;
            this.information = information;
        }
    }
}
