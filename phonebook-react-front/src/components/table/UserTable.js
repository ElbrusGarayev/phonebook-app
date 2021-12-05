import React from 'react';

const UserTable = props => (
  
    <table className="responsive-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Action</th>
            </tr>
        </thead>
    <tbody>
        {
            props.users.length > 0 ? (
                props.users.map (user => (

                    <tr key={user.userId}>
                        <td>{user.userId}</td>
                        <td>{user.name}</td>
                        <td>{user.phone}</td>
                        <td className="center-align">
                            <button 
                                className="waves-effect waves-light btn-small"
                                onClick={() => props.editRow(user)}>
                                edit
                            </button>

                            <button 
                                className="waves-effect waves-light btn-small red darken-4"
                                onClick={() => props.deleteUser(user.userId)}>
                                delete
                            </button>
                        </td> 
                    </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan={3}>{props.users[0]} No Users</td>
                    </tr>
                )
        }          
    </tbody>
  </table>
);
    
export default UserTable;