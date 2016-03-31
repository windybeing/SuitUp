using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Service
{
    //卖家服务
    public interface SellerService
    {
        string Login(string Username, string Password);
        string ModifyInfor(string ClientName, int Age,string Address,string Password);
        void GetSellerInfor(string ReturnMessage);
        string Register(string username, string password, string Email, string PhoneNumber, string identification);
        string GetFailedMessage(string ReturnMessage);
        bool IsSuccess(string retMessage);
    }
}
