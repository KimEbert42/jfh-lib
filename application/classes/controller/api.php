<?php defined('SYSPATH') or die('No direct script access.');

abstract class Controller_Api extends Controller 
{
	protected $user = null;

        public function before()
        {
		$user = Auth::instance()->get_user();

		if ($user)
		{
			//Are we already logged in?
		}
		elseif (!isset($_SERVER['PHP_AUTH_USER']))
		{
			header('WWW-Authenticate: Basic realm="My Realm"');
			throw new HTTP_Exception_401("Unauthorized");
		}
		else
		{
			Auth::instance()->login($_SERVER['PHP_AUTH_USER'], $_SERVER['PHP_AUTH_PW']);
			$user = Auth::instance()->get_user();
		}

		// if a user is not logged in, throw exception requiring auth
		if (!$user)
		{
			header('WWW-Authenticate: Basic realm="My Realm"');
			throw new HTTP_Exception_401("Unauthorized");
			//Request::current()->redirect('user/login');
		}

		// TODO Insert rate limiting here...
		// TODO Rate limiting will use innodb table to start with.

		$this->user = $user;

		return parent::before();
        }

	protected function result($var)
	{
		$this->response->body(json_encode($var));
		$this->response->headers('Content-Type', 'application/json');
	}

        public function after()
        {
		return parent::after();
        }


}
