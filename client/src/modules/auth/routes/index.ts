import { lazy } from 'react';
const Login = lazy(() => import('../components/login/Login'));
const MfaCode = lazy(() => import('../components/mfa-code/MfaCode'));
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
    },
    {
        path: "/login-mfa",
        component: MfaCode,
        status: 'GUEST'
    }
    
]

export default authRoutes;