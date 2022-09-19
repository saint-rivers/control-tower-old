package com.saintrivers.controltower.common.exception.user

class NotLoggedInException : SecurityException("no requester was found in the request")