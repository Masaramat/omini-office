import { email, password } from "../assets/icons"
import {headerLogo} from "../assets/images"
import Button from "../components/Button"
const Login = () => {
  return (
    <div className="flex justify-center m-16 ">
      <div className="flex flex-col justify-start lg:min-w-[450px] min-w-[400px] lg:min-h-[450px] min-h-[400px] border border-orange-600 rounded-b-lg bg-gray-100">
        <div className="min-h-16 w-full bg-gray-700 mt-0 rounded-b-xl">
          <h3 className="text-white text-center text-3xl pt-2">Login</h3>
        </div>
        <img src={headerLogo} alt="Logo" width={300} height={200} className="mx-auto mt-3"/>
        <h2 className="text-center fonts-monserat font-bold text-3xl mt-3">OfficePro</h2>

        <form action="" method="post">
          <div className="flex flex-col p-8">
            <div className="flex flex-auto border border-slate-gray rounded-full h-10 m-2">
              <img src={email} alt=""  className="pl-5 bg-gray-700 rounded-l-full"/>
              <input type="text" name="email" id="email" className="input" />
            </div>

            <div className="flex flex-auto border border-slate-gray rounded-full h-10 m-2">
              <img src={password} alt="" className="pl-5 bg-gray-700 rounded-l-full"/>
              <input type="password" name="password" id="password" className="input" />
            </div>
            <div className="flex-1 justify-start mx-2 m-2">
              <input type="checkbox" name="password" id="remember" className="input" />
              <label htmlFor="" className="mx-2">Remember me</label>
            </div>
           <Button 
            label="Login"
           />

           <p className=" text-center mt-3">Forgot password? Contact administrator</p>
           
            
           
          </div>
          

        </form>
       
      </div>
    </div>
   
  )
}

export default Login