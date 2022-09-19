package com.saintrivers.controltower.common.exception

class NotLoggedInException : SecurityException("no requester was found in the request")