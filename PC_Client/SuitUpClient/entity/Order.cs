﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Entity
{
    public class Order
    {
        public int ordernumber { get; set; }
        public string alipay {get;set;}
        public string address { get; set; }
        public double amount { get; set; }
        public string postcode { get; set; }
        public ShoppingCart clothes {get;set;}
        public string phonenumber{get;set;}
        public string recipient {get;set;}
        public string time { get; set; }
        //public Order(int ordernumber,string address, double amount )

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
