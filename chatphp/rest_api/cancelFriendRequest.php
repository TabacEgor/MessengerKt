<?php

include __DIR__ . "/GeneralFunctions.php";
include __DIR__ . "/utilities/firebaseNotification.php";
$response = array();

if (isset($_POST['friends_id']) && isset($_POST['approved_user_id']) && isset($_POST['user_id']) && isset($_POST['token'])) {
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $friends_id = $_POST['friends_id'];
    $approved_user_id = $_POST['approved_user_id'];
    $firebaseNotificationObject = new FirebaseNotificationClass();
    $generalFunctionsObject = new GeneralFunctionsClass();
    $dbOperationsObject = new DBOperations();

    $result_token = $dbOperationsObject->isTokenExist($approved_user_id, $token);
    if (mysqli_num_rows($result_token) > 0) {

        $request_user = $generalFunctionsObject->getUserById($user_id);
        $approved_user = $generalFunctionsObject->getUserById($approved_user_id);
        $approved_user_token = $approved_user["token"];
        $connection = $dbOperationsObject->deleteFriend($friends_id);

        if (mysqli_affected_rows($connection) > 0) {
            $obj = new stdClass();
            $obj->firebase_json_message = array(
                "type" => 'cancelFriendRequest',
                "friends_id" => $friends_id,
                "request_user" => $request_user
            );

            $messageFirbase = json_encode($obj);
            $firebaseNotificationObject->send_notification($approved_user_token, $messageFirbase);
            $response["success"] = 1;
            $response['friends_id'] = $friends_id;
            echo json_encode($response);
        } else {
            $response["success"] = 0;
            echo json_encode($response);
        }

    } else {
        $response["success"] = 0;
        $response["message"] = "Invalid token";
        echo json_encode($response);
    }
}
?>