import { lazy } from 'react';
const Login = lazy(() => import('../components/login/Login'));
const Signup = lazy(() => import('../components/signup/Signup'));

const authRoutes: any[] = [
    {
        path: "/signup",
        component: Signup,
        status: 'GUEST'
    },
    {
        path: "/login",
        component: Login,
        status: 'GUEST'
    }
    
]

export default authRoutes;