using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Entity
{
    public class ClientOrder
    {
        public string _id { get; set; }
        public List<Suit> orderItems { get; set; }
        public string address { get; set; }
        public string receiver { get; set; }
        public string phonenumber { get; set; }
        //todo
        public string time { get; set; }
        public double amount { get; set; }
        public double bust { get; set; }
        public double waistline { get; set; }
        public double hipline { get; set; }
        public double shoulder { get; set; }
        public double forebreast { get; set; }
        public double metathorax { get; set; }
        public double upperlimb { get; set; }
        public double lowerlimb { get; set; }
    }
}
