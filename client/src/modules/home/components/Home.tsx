import { AuthContext } from '@contexts/index'
import React, { useContext } from 'react'
import GuestHome from './guest-home/GuestHome'
import UserHome from './user-home/UserHome'

const Home = () => {
    const { user } = useContext(AuthContext)
  return user ? <UserHome/> : <GuestHome/>
}

export default Home