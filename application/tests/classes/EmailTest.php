<?php defined('SYSPATH') or die('No direct access allowed!'); 
  
class EmailTest extends Kohana_UnitTest_TestCase 
{ 
    public function test_email() 
    {
	$email = Kohana::$config->load('email.testemail');
	$this->assertTrue((bool)Email::send($email, $email, "Test Email", "Test Email", $html = false));
    } 
}
