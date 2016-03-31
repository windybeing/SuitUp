using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.Suitup.entity
{
    public class CurOrder
    {
        public string _id { get; set; }
        public string seller_id { get; set; }
        public String suitname { get; set; }
        public double price { get; set; }
        public string[] information { get; set; }
        public string photo_id { get; set; }
        public byte[] photo { get; set; }
        public int count { get; set; }
    }
}
