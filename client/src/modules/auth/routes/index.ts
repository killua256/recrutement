import { lazy } from 'react';

const Login = lazy(() => import('../components/login/Login'));
const Signup = lazy(() => import('../components/signup/Signup'));
const ActivateAccount = lazy(() => import('../components/activate-account/ActivateAccount'));
const MfaCode = lazy(() => import('../components/mfa-code/MfaCode'));

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
    },
    {
        path: '/activate/:token',
        component: ActivateAccount,
        status: 'GUEST'
    }
    
]

export default authRoutes;