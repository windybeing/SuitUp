using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Chpoi.SuitUp.Entity;

namespace Chpoi.SuitUp.Entity
{
    public class ShoppingCart
    {
        public List<ShoppingCartItem> ShoppingCartItems { get; set; }
        public ShoppingCart() { this.ShoppingCartItems = new List<ShoppingCartItem>(); }
        public double amount {get;set;}
     }
}
