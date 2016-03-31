using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Chpoi.SuitUp.Service
{
    //客户端服务
    public interface ClientService
    {
        string Login(string Username, string Password);
        string ModifyInfor(string ClientName, int Age,string Address,string Password);
        void GetUserInfor(string ReturnMessage);
        string Register(string username, string password, string Email,string PhoneNumber);
        bool IsSuccess(string ReturnMessage);
        string GetFailedMessage(string ReturnMessage);
    }
}
