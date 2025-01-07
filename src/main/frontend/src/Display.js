import React from 'react'
export default function Display({output}) {
    if (output === null || output === undefined) {
        return <h4>No data yet</h4>          
    }
    return (
        <div>
            <h3 style={{whiteSpace: 'pre-line'}}>
                {output.outputCode}
            </h3>
        </div>
    );   
}