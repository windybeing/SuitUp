using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Entity
{
    public class Client
    {
        public string _id { get; set; }
        public string username { get; set; }
        public string password { get; set; }
        public string email { get; set; }
        public string phonenumber { get; set; }
        public string clientname { get; set; }
        public int age { get; set; }
        public double account { get; set; }
        public string address { get; set; }
        public string frontphotopath { get; set; }
        public string sidephotopath { get; set; }
        public double height { get; set; }
        public double bust { get; set; }
        public double waistline { get; set; }
        public double hipline { get; set; }
        public double shoulder { get; set; }
        public double forebreast { get; set; }
        public double metathorax { get; set; }
        public double upperlimb { get; set; }
        public double lowerlimb { get; set; }
        public Client() { }
        public Client(string password,string clientname, int age, string address)
        {
            this.password = password;
            this.clientname = clientname;
            this.age = age;
            this.address = address;
        }
        //public List<string> avatar { get; set; } //头像
        //public List<string> parameters_id { get; set; }
        //public List<string> orders_id { get; set; }
        //public List<string> collection_id { get; set; }
    }
}
