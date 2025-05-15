import React, { useEffect } from 'react'
import { useState } from 'react'
import { getTodo, saveTodo, updateTodo } from '../services/TodoService'
import { useNavigate, useParams } from 'react-router-dom'

const TodoComponent = () => {

    const [email, setEmail] = useState('')
    const [description, setDescription] = useState('')
    const [completed, setCompleted] = useState(false)
    const navigate = useNavigate()
    const { id } = useParams()


    function saveOrUpdateTodo(e){
        e.preventDefault()

        const todo = {email, description, completed}
        console.log(todo);
        if (validateEmailInput(email)) {
            if(id){

                updateTodo(id, todo).then((response) => {
                    navigate('/todos')
                }).catch(error => {
                    console.error(error);
                })
    
            }else{
                saveTodo(todo).then((response) => {
                    console.log(response.data)
                    navigate('/todos')
                }).catch(error => {
                    console.error(error);
                })
            }
        }
    }

    function pageTitle(){
        if(id) {
            return <h2 className='text-center'>Update Todo</h2>
        }else {
            return <h2 className='text-center'>Add Todo</h2>
        }
    }

    function validateEmailInput(emailInput) {
        const feedbackElement = document.getElementById('emailFeedback');
        const emailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      
        if (emailRegex.test(emailInput)) {
          feedbackElement.textContent = ""; // Email is valid
          return true
        } else {
          feedbackElement.textContent = "Please enter a valid email address.";
          return false
        }
      }

    useEffect( () => {

        if(id){
            getTodo(id).then((response) => {
                console.log(response.data)
                setEmail(response.data.email)
                setDescription(response.data.description)
                setCompleted(response.data.completed)
            }).catch(error => {
                console.error(error);
            })
        }

    }, [id])

  return (
    <div className='container'>
        <br /> <br />
        <div className='row'>
            <div className='card col-md-6 offset-md-3 offset-md-3'>
                { pageTitle() }
                <div className='card-body'>
                    <form>
                        <div className='form-group mb-2'>
                            <label className='form-label'>Todo Email:</label>
                            <input
                                type='text'
                                className='form-control'
                                placeholder='Enter Todo Email'
                                name='email'
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            >
                            </input>
                        </div>

                        <div className='form-group mb-2'>
                            <label className='form-label'>Todo Description:</label>
                            <input
                                type='text'
                                className='form-control'
                                placeholder='Enter Todo Description'
                                name='description'
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                            >
                            </input>
                        </div>

                        <div className='form-group mb-2'>
                            <label className='form-label'>Todo Completed:</label>
                            <select
                                className='form-control'
                                value={completed}
                                onChange={(e) => setCompleted(e.target.value)}
                            >
                                <option value="false">No</option>
                                <option value="true">Yes</option>

                            </select>
                        </div>

                        <label id='emailFeedback'></label>

                        <button className='btn btn-success' onClick={ (e) => saveOrUpdateTodo(e)}>Submit</button>
                    </form>

                </div>
            </div>

        </div>
    </div>
  )
}

export default TodoComponent