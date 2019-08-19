<?php
require_once      __DIR__."/phpmailer/PHPMailerAutoload.php"; 
class PHPMailSend {
  function __construct() {

  }
    /**
     * Sending Push Notification
     */
    public function send_email($email,$message) {
      $mail = new PHPMailer; 
//comment following line when upload online not on local host
      $mail->isSMTP();
      $mail->Host = 'smtp.gmail.com';
      $mail->SMTPAuth = true;
      $mail->Username = 'fandroid@gmail.com';
      $mail->Password = 'qwerty';
      $mail->Port=587;
      $mail->SMTPSecure = 'tls'; 
      $mail->From = 'fandroid@gmail.com';
      $mail->FromName = 'Chat By FireBase Cloud Messaging App Staff';
      $mail->addAddress($email); 
      $mail->WordWrap = 50;
      $mail->isHTML(true); 
      $mail->Subject = 'Chat By FireBase Cloud Messaging App';
      $mail->Body    = 'this is your password : '.$message . ", You can change it from your profile";
      if(!$mail->send()) 
      {
        return 0 ;
   //echo 'Message could not be sent.';
   // echo 'Mailer Error: ' . $mail->ErrorInfo;
        exit;
      }
   //echo 'Message has been sent';        
      return 1 ;
    }
  }
  ?>