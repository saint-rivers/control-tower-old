package com.saintrivers.controltower.exception

class NotLoggedInException : SecurityException("no requester was found in the request")